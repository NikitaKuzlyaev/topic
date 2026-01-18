import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import config, { getApiUrl } from './config';
import HttpClient from './services/HttpClient';

function AllBoards() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  useEffect(() => {
    let mounted = true;

    const fetchThreads = async () => {
      setLoading(true);
      try {
        const url = getApiUrl('/api/board');
        const json = await HttpClient.get(url);
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
          <h2 className="text-2xl font-semibold">All public boards</h2>
          <Link to="/board/create" className="inline-block bg-blue-600 text-white px-3 py-1 rounded-md font-semibold hover:bg-blue-500">+ Create Board</Link>
        </div>

        {loading && <p className="text-gray-600">Loading...</p>}
        {error && <p className="text-red-600">Error: {error}</p>}

        {data && (
          <section className="w-full">
            <ul className="flex flex-col divide-y divide-gray-100 border border-gray-100 overflow-hidden">
              {Array.isArray(data.threads) && data.threads.length > 0 ? (
                data.threads.map((t) => (
                  <li 
                    key={t.id} 
                    className="w-full bg-white pl-4 hover:bg-gray-200 transition-colors duration-0"
                  >
                    <div className="text-lg font-semibold text-gray-900">
                      <Link to={`/board/${t.id}`} className="hover:text-blue-600 block">{t.title}</Link>
                    </div>
                  </li>
                ))
              ) : (
                <li className="text-gray-500 p-4">No threads found</li>
              )}
            </ul>

            <div className="text-gray-500 mb-4 mt-4">
              <strong>Page:</strong> {data.pageInfo?.currentPage + 1} / {Math.max(0, (data.pageInfo?.totalPages + 1 || 1) - 1)}
              &nbsp; <strong>Size:</strong> {data.pageInfo?.pageSize}
            </div>
            
          </section>
          
        )}
      </div>
    </main>
  );
}

export default AllBoards;
