import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './Board.css';
import config, { getApiUrl } from './config';

function Board() {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  useEffect(() => {
    let mounted = true;

    const fetchBoard = async () => {
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
    };

    fetchBoard();

    return () => {
      mounted = false;
    };
  }, [id]);

  return (
    <main className="board-root">
      {loading && <p>Loading...</p>}
      {error && <p className="board-error">Error: {error}</p>}

      {data && (
        <section className="board-card">
          <h2 className="board-title">{data.title || `Board ${id}`}</h2>
          <div className="board-meta">id: {data.id}</div>
          <pre className="board-json">{JSON.stringify(data, null, 2)}</pre>
        </section>
      )}
    </main>
  );
}

export default Board;
