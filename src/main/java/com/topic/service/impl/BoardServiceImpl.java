package com.topic.service.impl;

import com.topic.entity.main.Board;
import com.topic.entity.main.Publication;
import com.topic.entity.main.User;
import com.topic.repository.BoardRepository;
import com.topic.repository.PublicationRepository;
import com.topic.repository.UserRepository;
import com.topic.service.BoardService;
import com.topic.service.dto.BoardDto;
import com.topic.service.dto.BoardWithAllPublicationsDto;
import com.topic.service.dto.CreateBoardDto;
import com.topic.service.dto.PaginatedBoardDto;
import com.topic.service.helpers.BoardServiceImplHelper;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final PublicationRepository publicationRepository;

    @Autowired
    public BoardServiceImpl(
            BoardRepository boardRepository,
            UserRepository userRepository,
            PublicationRepository publicationRepository
    ) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
    }

    @Override
    public BoardDto createBoard(CreateBoardDto data) {

        Board board = new Board();

        board.setTitle(data.title());

        Optional<User> author = userRepository.findById(data.userId());
        if (author.isEmpty()){
            throw new RuntimeException("");
        }

        board.setAuthor(author.get());

        var res = boardRepository.save(board);
        return BoardServiceImplHelper.mapToBoardDto(res);
    }

    @Override
    public BoardDto getBoard(Long boardId) throws EntityExistsException {
        Optional<Board> data = boardRepository.findById(boardId);
        if (data.isEmpty()) {
            throw new EntityExistsException();
        }
        return BoardServiceImplHelper.mapToBoardDto(data.get());
    }

    @Override
    public PaginatedBoardDto getBoards(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return BoardServiceImplHelper.mapToPaginatedBoardDto(boardPage, page, pageSize);
    }

    public BoardWithAllPublicationsDto getBoardWithAllPublications(Long boardId){
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()) {
            throw new EntityExistsException();
        }
        List<Publication> publications = publicationRepository.findAllByBoardIdWithAuthor(boardId);
        return BoardServiceImplHelper.mapToBoardWithAllPublicationsDto(board.get(), publications);
    }
}
