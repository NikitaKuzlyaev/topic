import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import config, { getApiUrl } from './config';

function CreateBoard() {
  const [title, setTitle] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    if (!title || title.trim() === '') {
      setError('Title is required');
      return;
    }
    setLoading(true);
    try {
      const url = getApiUrl('/api/board');
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: title.trim() }),
      });
      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
      const data = await response.json();
      const newId = data?.id;
      if (newId) {
        navigate(`/board/${newId}`);
      } else {
        navigate('/all-boards');
      }
    } catch (err) {
      setError(err.message || 'Create failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="min-h-screen bg-gray-50 py-24 px-4 sm:px-6 lg:px-8">
      <div className="max-w-2xl mx-auto w-full">
        <h2 className="text-2xl font-semibold mb-4 text-gray-900">Create Board</h2>

        <form className="bg-white border border-gray-100 p-6 rounded-md" onSubmit={handleSubmit}>
          <label className="block mb-4">
            <div className="font-medium mb-2 text-gray-900">Title</div>
            <input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Enter board title"
              className="w-full p-2 border rounded-md"
            />
          </label>

          {error && <div className="text-red-600 mb-2">{error}</div>}

          <div>
            <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded-md" disabled={loading}>
              {loading ? 'Creating…' : 'Create'}
            </button>
          </div>
        </form>
      </div>
    </main>
  );
}

export default CreateBoard;
