import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

function Header() {
  return (
    <header className="header">
      <nav style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
        <Link to="/" style={{ textDecoration: 'none' }}>
          <h1 className='TopicLabel'>Topic</h1>
        </Link>
        <Link to="/all-boards" className="header-link">All Boards</Link>
        <Link to="/all-boards" className="header-link">All Boards</Link>
        <Link to="/all-boards" className="header-link">All Boards</Link>
      </nav>
    </header>
  );
}

export default Header;
