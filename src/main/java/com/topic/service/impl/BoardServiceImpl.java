package com.topic.service.impl;

import com.topic.entity.Board;
import com.topic.entity.Publication;
import com.topic.entity.User;
import com.topic.repository.BoardRepository;
import com.topic.repository.PublicationRepository;
import com.topic.repository.UserRepository;
import com.topic.service.BoardService;
import com.topic.service.dto.*;
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

        // TODO: используется тестовый пользователь как автор ЛЮБЫХ досок
        Optional<User> author = userRepository.findById(1L);
        if (author.isEmpty()){
            throw new RuntimeException("");
        }

        board.setAuthor(author.get());

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

    public BoardWithAllPublicationsDto getBoardWithAllPublications(Long boardId){
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()) {
            throw new EntityExistsException();
        }
        List<Publication> publications = publicationRepository.findAllByBoardIdWithAuthor(boardId);
        return Util.mapToBoardWithAllPublicationsDto(board.get(), publications);
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

    // todo: Это не должно быть здесь. как и самого класса Util...
    static PublicationsListDto mapToPublicationsListDto(List<Publication> publicationList){
        List<PublicationDto> publications = publicationList.stream().map(Util::mapToPublicationDto).toList();
        return new PublicationsListDto(publications);
    }

    static PublicationDto mapToPublicationDto(Publication publication){
        return new PublicationDto(publication.getId(), publication.getAuthor().getUsername(), publication.getContent());
    }

    static BoardWithAllPublicationsDto mapToBoardWithAllPublicationsDto(Board board, List<Publication> publicationList){

        BoardDto boardDto = Util.mapToBoardDto(board);
        PublicationsListDto publicationsListDto = Util.mapToPublicationsListDto(publicationList);

        return new BoardWithAllPublicationsDto(
                boardDto, publicationsListDto
        );
    }
}
