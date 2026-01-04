package com.topic.service.impl;

import com.topic.entity.Board;
import com.topic.repository.BoardRepository;
import com.topic.service.BoardService;
import com.topic.service.dto.CreateBoardDto;
import com.topic.service.dto.PaginatedBoardDto;
import com.topic.service.dto.BoardDto;
import com.topic.util.exeptions.NotImplementedException;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public BoardDto createBoard(CreateBoardDto data) {

        Board board = new Board();
        board.setTitle(data.title());

        var res = boardRepository.save(board);
        return Util.mapToBoardDto(res);
    }

    @Override
    public BoardDto getBoard(Long boardId) throws EntityExistsException {
        Optional<Board> data = boardRepository.findById(boardId);
        if (data.isEmpty()){
            throw new EntityExistsException();
        }
        return Util.mapToBoardDto(data.get());
    }

    @Override
    public PaginatedBoardDto getBoards(int page, int pageSize) {
        // TODO
        return null;
    }
}

class Util{
    static BoardDto mapToBoardDto(Board data){
        throw new NotImplementedException("");
    }
}
