import Header from './Header';
import Home from './Home';
import AllBoards from './AllBoards';
import Board from './Board';
import CreateBoard from './CreateBoard';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <Router>
      <div className="App relative z-10">
        <Header />
        <div className="mx-auto w-2/3">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/all-boards" element={<AllBoards />} />
            <Route path="/board/:id" element={<Board />} />
            <Route path="/board/create" element={<CreateBoard />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
