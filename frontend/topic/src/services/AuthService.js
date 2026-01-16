// Simple AuthService: single responsibility for token storage/retrieval
const ACCESS_KEY = 'accessToken';
const REFRESH_KEY = 'refreshToken';
const USER_ID = 'userId';
const USER_NAME = 'userName';

export function getAccessToken() {
  return localStorage.getItem(ACCESS_KEY);
}

export function setAccessToken(token) {
  if (token == null) {
    localStorage.removeItem(ACCESS_KEY);
  } else {
    localStorage.setItem(ACCESS_KEY, token);
  }
}

export function getRefreshToken() {
  return localStorage.getItem(REFRESH_KEY);
}

export function setRefreshToken(token) {
  if (token == null) {
    localStorage.removeItem(REFRESH_KEY);
  } else {
    localStorage.setItem(REFRESH_KEY, token);
  }
}

export function clearAuth() {
  localStorage.removeItem(ACCESS_KEY);
  localStorage.removeItem(REFRESH_KEY);
  localStorage.removeItem(USER_ID);
  localStorage.removeItem(USER_NAME);
}

export function setUserInfo({ userId, name }) {
  if (userId != null) localStorage.setItem(USER_ID, userId.toString());
  if (name != null) localStorage.setItem(USER_NAME, name);
}

export function getUserId() {
  return localStorage.getItem(USER_ID);
}

export function getUserName() {
  return localStorage.getItem(USER_NAME);
}

export default {
  getAccessToken,
  setAccessToken,
  getRefreshToken,
  setRefreshToken,
  clearAuth,
  setUserInfo,
  getUserId,
  getUserName,
};
