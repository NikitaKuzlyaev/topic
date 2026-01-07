import React from 'react';
import { Link } from 'react-router-dom';

function Header() {
  return (
    <header className="fixed top-0 left-0 w-full bg-zinc-900 text-white z-50 shadow">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        <nav className="h-14 flex items-center justify-between">
          <Link to="/" className="flex items-center gap-3">
            <h1 className="text-2xl font-bold text-yellow-300">Topic</h1>
          </Link>

          <div className="flex items-center gap-4">
            <Link to="/all-boards" className="text-sm font-medium text-gray-200 hover:text-white">All Boards</Link>
            <Link to="/board/create" className="text-sm font-medium text-gray-200 hover:text-white">Create</Link>
          </div>
        </nav>
      </div>
    </header>
  );
}

export default Header;
