import React, { useEffect, useState } from 'react';
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
    <main className="min-h-screen bg-gray-50 text-gray-900 py-24 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto w-full">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-2xl font-semibold">All Boards</h2>
          <Link to="/board/create" className="inline-block bg-blue-600 text-white px-3 py-1 rounded-md font-semibold hover:bg-blue-500">+ Create Board</Link>
        </div>

        {loading && <p className="text-gray-600">Loading...</p>}
        {error && <p className="text-red-600">Error: {error}</p>}

        {data && (
          <section className="w-full">
            <div className="text-gray-500 mb-4">
              <strong>Page:</strong> {data.pageInfo?.currentPage} / {Math.max(0, (data.pageInfo?.totalPages || 0) - 1)}
              &nbsp; <strong>Size:</strong> {data.pageInfo?.pageSize}
            </div>

            <ul className="flex flex-col gap-1">
              {Array.isArray(data.threads) && data.threads.length > 0 ? (
                data.threads.map((t) => (
                  <li key={t.id} className="w-full bg-white border border-gray-100 rounded-sm p-2 shadow-sm">
                    <div className="text-lg font-semibold text-gray-900 mb-1">
                      <Link to={`/board/${t.id}`} className="hover:text-blue-600">{t.title}</Link>
                    </div>
                  </li>
                ))
              ) : (
                <li className="text-gray-500">No threads found</li>
              )}
            </ul>
          </section>
        )}
      </div>
    </main>
  );
}

export default AllBoards;
