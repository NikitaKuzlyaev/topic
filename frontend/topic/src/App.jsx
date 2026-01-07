import Header from './Header';
import Home from './Home';
import AllBoards from './AllBoards';
import Board from './Board';
import CreateBoard from './CreateBoard';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/all-boards" element={<AllBoards />} />
          <Route path="/board/:id" element={<Board />} />
          <Route path="/board/create" element={<CreateBoard />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
