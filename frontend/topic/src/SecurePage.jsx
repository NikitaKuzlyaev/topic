import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getApiUrl } from './config';

function SecurePage() {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [authInfo, setAuthInfo] = useState({});
  const navigate = useNavigate();

  const getAccessToken = () => localStorage.getItem('accessToken');
  const getRefreshToken = () => localStorage.getItem('refreshToken');
  const getUserId = () => localStorage.getItem('userId');
  const getUserName = () => localStorage.getItem('userName');

  useEffect(() => {
    fetchUserData();
    
    setAuthInfo({
      accessToken: getAccessToken(),
      refreshToken: getRefreshToken(),
      userId: getUserId(),
      userName: getUserName(),
      hasToken: !!getAccessToken()
    });
  }, []);

  const fetchUserData = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const token = getAccessToken();
      
      if (!token) {
        setError('No access token found. Please login first.');
        setLoading(false);
        return;
      }

      const url = getApiUrl('/api/auth/me');
      console.log('Fetching user data from:', url);
      console.log('Using token:', token ? `${token.substring(0, 20)}...` : 'none');

      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Accept': 'application/json'
        }
      });

      console.log('Response status:', response.status);
      console.log('Response headers:', Object.fromEntries(response.headers.entries()));

      if (!response.ok) {
        if (response.status === 401) {
          throw new Error('Unauthorized - Invalid or expired token');
        }
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      console.log('User data received:', data);
      setUserData(data);
      
    } catch (err) {
      console.error('Error fetching user data:', err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    
    navigate('/login');
  };

  const handleRefresh = () => {
    fetchUserData();
  };

  const handleLoginRedirect = () => {
    navigate('/login');
  };

  const copyToClipboard = (text) => {
    navigator.clipboard.writeText(text);
    alert('Copied to clipboard!');
  };

  return (
    <main className="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-4xl mx-auto">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2 mt-4">Authentication Debug</h1>
          <p className="text-gray-600">Debug page for /api/auth/me endpoint</p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">

          <div className="bg-white border border-gray-200 rounded-lg shadow-sm p-6">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Authentication State</h2>
            
            <div className="space-y-4">
              <div>
                <h3 className="font-medium text-gray-700 mb-2">LocalStorage Data</h3>
                <div className="bg-gray-50 rounded-md p-4 space-y-2">
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-600">Has Access Token:</span>
                    <span className={`px-2 py-1 text-xs rounded ${authInfo.hasToken ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                      {authInfo.hasToken ? 'YES' : 'NO'}
                    </span>
                  </div>
                  
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-600">User ID:</span>
                    <span className="font-mono text-sm">{authInfo.userId || 'Not set'}</span>
                  </div>
                  
                  <div className="flex justify-between items-center">
                    <span className="text-sm text-gray-600">Username:</span>
                    <span className="font-mono text-sm">{authInfo.userName || 'Not set'}</span>
                  </div>
                </div>
              </div>

              <div>
                <h3 className="font-medium text-gray-700 mb-2">Access Token</h3>
                <div className="bg-gray-900 text-gray-100 rounded-md p-3 overflow-x-auto">
                  <code className="text-sm break-all">
                    {authInfo.accessToken 
                      ? `${authInfo.accessToken.substring(0, 50)}...` 
                      : 'No token'}
                  </code>
                </div>
                {authInfo.accessToken && (
                  <button
                    onClick={() => copyToClipboard(authInfo.accessToken)}
                    className="mt-2 text-sm text-blue-600 hover:text-blue-800"
                  >
                    Copy full token
                  </button>
                )}
              </div>

              <div className="flex space-x-3">
                <button
                  onClick={handleRefresh}
                  disabled={loading}
                  className="flex-1 bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 disabled:opacity-50"
                >
                  {loading ? 'Refreshing...' : 'Refresh Data'}
                </button>
                
                {authInfo.hasToken ? (
                  <button
                    onClick={handleLogout}
                    className="flex-1 bg-red-600 text-white py-2 px-4 rounded-md hover:bg-red-700"
                  >
                    Logout
                  </button>
                ) : (
                  <button
                    onClick={handleLoginRedirect}
                    className="flex-1 bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700"
                  >
                    Go to Login
                  </button>
                )}
              </div>
            </div>
          </div>

          <div className="bg-white border border-gray-200 rounded-lg shadow-sm p-6">
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-xl font-semibold text-gray-800">API Response</h2>
              <div className="text-sm">
                <span className={`px-2 py-1 rounded ${loading ? 'bg-yellow-100 text-yellow-800' : error ? 'bg-red-100 text-red-800' : 'bg-green-100 text-green-800'}`}>
                  {loading ? 'LOADING' : error ? 'ERROR' : 'SUCCESS'}
                </span>
              </div>
            </div>

            {loading ? (
              <div className="text-center py-8">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
                <p className="mt-4 text-gray-600">Fetching user data...</p>
              </div>
            ) : error ? (
              <div className="bg-red-50 border border-red-200 rounded-md p-4">
                <h3 className="font-medium text-red-800 mb-2">Error Details</h3>
                <p className="text-red-600">{error}</p>
                <p className="mt-2 text-sm text-red-700">
                  Check if you are logged in and the token is valid.
                </p>
              </div>
            ) : userData ? (
              <div>
                <div className="bg-green-50 border border-green-200 rounded-md p-4 mb-4">
                  <h3 className="font-medium text-green-800 mb-2">✓ User Data Retrieved Successfully</h3>
                  <p className="text-sm text-green-700">Endpoint: /api/auth/me</p>
                </div>

                <div className="space-y-4">
                  <div>
                    <h4 className="font-medium text-gray-700 mb-2">User Information</h4>
                    <div className="bg-gray-50 rounded-md p-4 space-y-3">
                      {Object.entries(userData).map(([key, value]) => (
                        <div key={key} className="flex justify-between items-start border-b pb-2 last:border-0">
                          <span className="text-sm text-gray-600 capitalize">{key}:</span>
                          <span className="text-sm font-mono text-gray-800 max-w-xs break-all text-right">
                            {typeof value === 'object' ? JSON.stringify(value) : String(value)}
                          </span>
                        </div>
                      ))}
                    </div>
                  </div>

                  <div>
                    <h4 className="font-medium text-gray-700 mb-2">Raw Response</h4>
                    <div className="bg-gray-900 text-gray-100 rounded-md p-3 overflow-x-auto">
                      <pre className="text-sm">
                        {JSON.stringify(userData, null, 2)}
                      </pre>
                    </div>
                    <button
                      onClick={() => copyToClipboard(JSON.stringify(userData, null, 2))}
                      className="mt-2 text-sm text-blue-600 hover:text-blue-800"
                    >
                      Copy JSON
                    </button>
                  </div>
                </div>
              </div>
            ) : (
              <div className="text-center py-8 text-gray-500">
                No data available. Click "Refresh Data" to fetch user information.
              </div>
            )}

            
          </div>
        </div>

      </div>
    </main>
  );
}

export default SecurePage;