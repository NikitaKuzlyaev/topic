import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import './Board.css';
import config, { getApiUrl } from './config';

function Board() {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  const fetchBoard = useCallback(async () => {
    let mounted = true;
    setLoading(true);
    try {
      const url = getApiUrl(`/api/thread/${id}`);
      const response = await fetch(url, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
      });
      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
      const json = await response.json();
      if (mounted) {
        setData(json);
        setError(null);
      }
    } catch (err) {
      if (mounted) setError(err.message || 'Fetch error');
    } finally {
      if (mounted) setLoading(false);
    }
    return () => {
      mounted = false;
    };
  }, [id]);

  useEffect(() => {
    fetchBoard();
  }, [fetchBoard]);

  // Publication form state
  const [showForm, setShowForm] = useState(false);
  const [content, setContent] = useState('');
  const [pubLoading, setPubLoading] = useState(false);
  const [pubError, setPubError] = useState(null);

  const handleCreatePublication = async (e) => {
    e && e.preventDefault();
    setPubError(null);
    if (!content || content.trim() === '') {
      setPubError('Content is required');
      return;
    }
    if (!id) {
      setPubError('Board id missing');
      return;
    }
    setPubLoading(true);
    try {
      const url = getApiUrl('/api/publication');
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ content: content.trim(), boardId: Number(id) }),
      });
      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
      // assume success - refresh board
      setContent('');
      setShowForm(false);
      await fetchBoard();
    } catch (err) {
      setPubError(err.message || 'Create failed');
    } finally {
      setPubLoading(false);
    }
  };

  return (
    <main className="board-root">
      {loading && <p>Loading...</p>}
      {error && <p className="board-error">Error: {error}</p>}

      {data && (
        <section className="board-card">
          <h2 className="board-title">{data.boardInfo?.title || `Board ${id}`}</h2>
          <div className="board-meta">id: {data.boardInfo?.id}</div>

          <div className="publications">
            {Array.isArray(data.publications) && data.publications.length > 0 ? (
              data.publications.map((pub) => (
                <article key={pub.id} className="pub-card">
                  <div className="pub-header">
                    <span className="pub-author">{pub.author || 'anonymous'}</span>
                    <span className="pub-id">#{pub.id}</span>
                  </div>
                  <div className="pub-content">{pub.content}</div>
                </article>
              ))
            ) : (
              <div className="pub-empty">No publications</div>
            )}
          </div>
          <div className="pub-actions">
            <button className="btn" onClick={() => setShowForm((s) => !s)}>
              {showForm ? 'Cancel' : 'Add Publication'}
            </button>
          </div>

          {showForm && (
            <form className="pub-form" onSubmit={handleCreatePublication}>
              <label className="pub-field">
                <div className="label">Content</div>
                <textarea
                  value={content}
                  onChange={(e) => setContent(e.target.value)}
                  rows={4}
                  placeholder="Enter publication content"
                />
              </label>
              {pubError && <div className="error">{pubError}</div>}
              <div className="actions">
                <button type="submit" className="btn" disabled={pubLoading}>
                  {pubLoading ? 'Posting…' : 'Post Publication'}
                </button>
              </div>
            </form>
          )}
        </section>
      )}
    </main>
  );
}

export default Board;
