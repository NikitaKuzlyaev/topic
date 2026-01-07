import React, { useEffect, useState } from 'react';
import './AllBoards.css';
import { Link } from 'react-router-dom';
import config, { getApiUrl } from './config';

function AllBoards() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  useEffect(() => {
    let mounted = true;

    const fetchThreads = async () => {
      setLoading(true);
      try {
        const url = getApiUrl('/api/thread');
        const response = await fetch(url, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
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
    };

    fetchThreads();

    return () => {
      mounted = false;
    };
  }, []);

  return (
    <main className="allboards-root">
      <h2>All Boards</h2>
      <div style={{ margin: '10px 0' }}>
        <Link to="/board/create" className="ab-create-button">+ Create Board</Link>
      </div>
      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'red' }}>Error: {error}</p>}

      {data && (
        <section>
          <div className="ab-meta">
            <strong>Page:</strong> {data.pageInfo?.currentPage} / {Math.max(0, (data.pageInfo?.totalPages || 0) - 1)}
            &nbsp; <strong>Size:</strong> {data.pageInfo?.pageSize}
          </div>

          <ul className="ab-list">
            {Array.isArray(data.threads) && data.threads.length > 0 ? (
              data.threads.map((t) => (
                <li key={t.id} className="ab-item">
                  <div className="title">
                    <Link to={`/board/${t.id}`}>{t.title}</Link>
                  </div>
                  <div className="id">id: {t.id}</div>
                </li>
              ))
            ) : (
              <li className="ab-empty">No threads found</li>
            )}
          </ul>
        </section>
      )}
    </main>
  );
}

export default AllBoards;
