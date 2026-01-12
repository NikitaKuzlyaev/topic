import React from 'react';
import config, { getApiUrl } from './config';

function Home() {

    const handleGetThread = async () => {
        try {
            const url = getApiUrl('/api/thread');

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            
        } catch (error) {
        }
    };

    const handleSendAsyncTask = async () => {
        try {
            const url = getApiUrl('/api/async/test');

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            
        } catch (error) {
        }
    };

    return (
        <main className="min-h-screen flex items-start justify-center py-24 px-4 sm:px-6 lg:px-8 bg-sky-50">
            <div className="max-w-2xl w-full text-center">
                <button
                    onClick={handleGetThread}
                    className="inline-block bg-blue-600 text-white px-4 py-2 rounded-md font-medium hover:bg-blue-500"
                >
                    [GET] /api/thread
                </button>

                <button
                    onClick={handleSendAsyncTask}
                    className="inline-block bg-blue-600 text-white px-4 py-2 rounded-md font-medium hover:bg-blue-500"
                >
                    [GET] /async/test
                </button>

            </div>
        </main>
    );
}

export default Home;
