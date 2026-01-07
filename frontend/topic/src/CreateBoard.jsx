import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CreateBoard.css';
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
      const url = getApiUrl('/api/thread');
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
    <main className="create-root">
      <h2>Create Board</h2>

      <form className="create-form" onSubmit={handleSubmit}>
        <label className="field">
          <div className="label">Title</div>
          <input
            className="input"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="Enter board title"
          />
        </label>

        {error && <div className="error">{error}</div>}

        <div className="actions">
          <button type="submit" className="btn" disabled={loading}>
            {loading ? 'Creating…' : 'Create'}
          </button>
        </div>
      </form>
    </main>
  );
}

export default CreateBoard;
