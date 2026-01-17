package com.topic.service;

import com.topic.service.dto.*;
import jakarta.persistence.EntityExistsException;

public interface BoardService {

    BoardDto createBoard(CreateBoardDto data);

    BoardDto getBoard(Long boardId) throws EntityExistsException;

    PaginatedBoardDto getBoards(int page, int pageSize);

    BoardWithAllPublicationsDto getBoardWithAllPublications(Long boardId);

    void deleteBoard(DeleteBoardDto data);
}