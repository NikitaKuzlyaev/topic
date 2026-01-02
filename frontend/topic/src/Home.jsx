import React from 'react';
import './Home.css';
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


    return (
        <div className='HomeRoot'>
            <main className="home">
                
                <button 
                    className="api-button"
                    onClick={handleGetThread}
                >
                    [GET] /api/thread
                </button>

            </main>
        </div>
    );
}

export default Home;
