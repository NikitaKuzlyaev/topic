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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (data.isEmpty()) {
            throw new EntityExistsException();
        }
        return Util.mapToBoardDto(data.get());
    }

    @Override
    public PaginatedBoardDto getBoards(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return Util.mapToPaginatedBoardDto(boardPage, page, pageSize);
    }
}

class Util {
    static BoardDto mapToBoardDto(Board data) {
        return new BoardDto(
                data.getId(),
                data.getTitle(),
                data.getAuthor(),
                data.getParent(),
                data.getCreatedAt()
        );
    }

    static PaginatedBoardDto mapToPaginatedBoardDto(Page<Board> data, int page, int pageSize) {
        List<BoardDto> boardDto = data.stream()
                .map(el -> new BoardDto(
                        el.getId(), el.getTitle(), el.getAuthor(), el.getParent(), el.getCreatedAt())
                ).collect(Collectors.toList());

        // TODO Заглушка
        int totalPages = 10;

        return new PaginatedBoardDto(page, totalPages, pageSize, boardDto);
    }

}
