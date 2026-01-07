package com.topic.service;

import com.topic.service.dto.BoardWithAllPublicationsDto;
import com.topic.service.dto.CreateBoardDto;
import com.topic.service.dto.PaginatedBoardDto;
import com.topic.service.dto.BoardDto;
import jakarta.persistence.EntityExistsException;

public interface BoardService {

    BoardDto createBoard(CreateBoardDto data);

    BoardDto getBoard(Long boardId) throws EntityExistsException;

    PaginatedBoardDto getBoards(int page, int pageSize);

    BoardWithAllPublicationsDto getBoardWithAllPublications(Long boardId);
}