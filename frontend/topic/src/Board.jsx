import React, { useEffect, useState, useCallback } from 'react';
import { useParams, Link } from 'react-router-dom';
import CreateBoard from './CreateBoard';
import config, { getApiUrl } from './config';
import HttpClient from './services/HttpClient';

function Board() {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  // Publication form state
  const [showForm, setShowForm] = useState(false);
  const [content, setContent] = useState('');
  const [pubLoading, setPubLoading] = useState(false);
  const [pubError, setPubError] = useState(null);

  // Nested boards state
  const [nestedBoards, setNestedBoards] = useState([]);
  const [nestedLoading, setNestedLoading] = useState(false);
  const [nestedError, setNestedError] = useState(null);
  const [showCreateNested, setShowCreateNested] = useState(false);

  const fetchBoard = useCallback(async () => {
    let mounted = true;
    setLoading(true);
    try {
      const url = getApiUrl(`/api/board/${id}`);
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
    return () => {
      mounted = false;
    };
  }, [id]);

  const fetchNestedBoards = useCallback(async () => {
    if (!id) return;
    setNestedLoading(true);
    try {
      const url = getApiUrl(`/api/board?parentId=${id}`);
      const json = await HttpClient.get(url);
      const list = Array.isArray(json) ? json : json.threads || json.items || [];
      setNestedBoards(list);
      setNestedError(null);
    } catch (err) {
      setNestedError(err.message || 'Fetch nested boards failed');
    } finally {
      setNestedLoading(false);
    }
  }, [id]);

  useEffect(() => {
    fetchBoard();
  }, [fetchBoard]);

  useEffect(() => {
    fetchNestedBoards();
  }, [fetchNestedBoards]);

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
      await HttpClient.post(url, { content: content.trim(), boardId: Number(id) });
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

  const handleDeleteBoard = async () => {
    if (!window.confirm('Are you sure you want to delete this board? This action cannot be undone.')) {
      return;
    }
    
    try {
      const url = getApiUrl(`/api/board/${id}`);
      await HttpClient.delete(url);
      // После удаления перенаправляем на главную
      window.location.href = '/';
    } catch (err) {
      setError(err.message || 'Failed to delete board');
    }
  };

  return (
    <main className="min-h-screen bg-orange-50 py-16 px-2 sm:px-6 lg:px-8">
      <div className="w-full">
        {loading && <p className="text-gray-600">Loading...</p>}
        {error && <p className="text-red-600">Error: {error}</p>}

        {data && (
          <section className="bg-white border border-gray-100 rounded-sm p-2">
            <h2 className="text-2xl font-semibold mb-2 text-gray-700">{data.boardInfo?.title || `Board ${id}`}</h2>

            <div className="mt-4">
              
              <button className="inline-block bg-blue-600 text-white px-3 py-1 mb-4 rounded-sm font-medium hover:bg-blue-500" onClick={() => setShowForm((s) => !s)}>
                {showForm ? 'Cancel' : 'Add Publication'}
              </button>

              <button className="inline-block ml-2 bg-green-600 text-white px-3 py-1 mb-4 rounded-sm font-medium hover:bg-green-500" onClick={() => setShowCreateNested((s) => !s)}>
                {showCreateNested ? 'Cancel' : '+ Create Nested Board'}
              </button>

              <button 
                className="inline-block ml-2 bg-red-600 text-white px-3 py-1 mb-4 rounded-sm font-medium hover:bg-red-500"
                onClick={handleDeleteBoard}
              >
                Delete Board
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

            {/* Nested boards list and create form */}
            <div className="mt-6">
              <h3 className="text-lg font-semibold mb-2 text-gray-700">Nested Boards</h3>
              {nestedLoading && <div className="text-gray-600">Loading nested boards...</div>}
              {nestedError && <div className="text-red-600">{nestedError}</div>}
              {Array.isArray(nestedBoards) && nestedBoards.length > 0 ? (
                <ul className="flex flex-col gap-2">
                  {nestedBoards.map((b) => (
                    <li key={b.id} className="w-full bg-white border border-gray-100 rounded-sm p-2 shadow-sm">
                      <div className="text-lg font-semibold text-gray-900 mb-1">
                        <Link to={`/board/${b.id}`} className="hover:text-blue-600">{b.title || `Board ${b.id}`}</Link>
                      </div>
                    </li>
                  ))}
                </ul>
              ) : (
                <div className="text-gray-500">No nested boards</div>
              )}

              {showCreateNested && (
                <div className="mt-4">
                  <CreateBoard boardId={id} inline onCreated={(newData) => {
                    const created = newData && newData.id ? newData : null;
                    if (created) {
                      setNestedBoards((prev) => [created, ...(prev || [])]);
                    } else {
                      fetchNestedBoards();
                    }
                    setShowCreateNested(false);
                  }} />
                </div>
              )}
            </div>
          </section>
        )}
      </div>
    </main>
  );
}

export default Board;
