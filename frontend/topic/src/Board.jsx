import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
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
      const url = getApiUrl(`/api/board/${id}`);
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
    <main className="min-h-screen bg-orange-50 py-16 px-2 sm:px-6 lg:px-8">
      <div className="mx-auto w-full">
        {loading && <p className="text-gray-600">Loading...</p>}
        {error && <p className="text-red-600">Error: {error}</p>}

        {data && (
          <section className="bg-white border border-gray-100 rounded-sm p-2">
            <h2 className="text-2xl font-semibold mb-2 text-gray-700">{data.boardInfo?.title || `Board ${id}`}</h2>

            <div className="mt-4">
              <button className="inline-block bg-blue-600 text-white px-3 py-1 mb-4 rounded-sm font-medium hover:bg-blue-500" onClick={() => setShowForm((s) => !s)}>
                {showForm ? 'Cancel' : 'Add Publication'}
              </button>
            </div>

            <div className="space-y-0">
              {Array.isArray(data.publications) && data.publications.length > 0 ? (
                data.publications.map((pub) => (
                  <article key={pub.id} className="bg-white border border-gray-100 p-4 rounded-sm">
                    <div className="flex items-center justify-between mb-2">
                      <span className="font-semibold text-gray-900">{pub.author || 'anonymous'}</span>
                      <span className="text-gray-500 text-sm">#{pub.id}</span>
                    </div>
                    <div className="text-gray-800 whitespace-pre-wrap">{pub.content}</div>
                  </article>
                ))
              ) : (
                <div className="text-gray-500">No publications</div>
              )}
            </div>

            {showForm && (
              <form className="mt-4 border-t pt-4" onSubmit={handleCreatePublication}>
                <label className="block mb-3">
                  <div className="font-medium mb-2">Content</div>
                  <textarea
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    rows={4}
                    placeholder="Enter publication content"
                    className="w-full p-2 border rounded-md"
                  />
                </label>
                {pubError && <div className="text-red-600 mb-2">{pubError}</div>}
                <div>
                  <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded-md" disabled={pubLoading}>
                    {pubLoading ? 'Posting…' : 'Post Publication'}
                  </button>
                </div>
              </form>
            )}
          </section>
        )}
      </div>
    </main>
  );
}

export default Board;
