import AuthService from './AuthService';

function buildHeaders(customHeaders = {}, body) {
  const headers = new Headers(customHeaders || {});

  // Add Authorization if token exists
  const token = AuthService.getAccessToken();
  if (token) headers.set('Authorization', `Bearer ${token}`);

  // If body is present and not FormData, ensure JSON content type
  if (body && !(body instanceof FormData) && !headers.has('Content-Type')) {
    headers.set('Content-Type', 'application/json');
  }

  return headers;
}

async function request(url, options = {}) {
  const { method = 'GET', headers: customHeaders, body, ...rest } = options;
  const headers = buildHeaders(customHeaders, body);

  const fetchOptions = { method, headers, ...rest };
  if (body) fetchOptions.body = body instanceof Object && !(body instanceof FormData) ? JSON.stringify(body) : body;

  const res = await fetch(url, fetchOptions);

  if (!res.ok) {
    const ct = res.headers.get('content-type') || '';
    let errBody = null;
    if (ct.includes('application/json')) errBody = await res.json().catch(() => null);
    else errBody = await res.text().catch(() => null);
    const message = (errBody && errBody.message) || `HTTP error! status: ${res.status}`;
    const error = new Error(message);
    error.status = res.status;
    error.body = errBody;
    throw error;
  }

  const contentType = res.headers.get('content-type') || '';
  if (contentType.includes('application/json')) return res.json();
  return res.text();
}

const HttpClient = {
  request,
  get: (url, opts = {}) => request(url, { ...opts, method: 'GET' }),
  post: (url, body, opts = {}) => request(url, { ...opts, method: 'POST', body }),
  put: (url, body, opts = {}) => request(url, { ...opts, method: 'PUT', body }),
  del: (url, opts = {}) => request(url, { ...opts, method: 'DELETE' }),
};

export default HttpClient;
