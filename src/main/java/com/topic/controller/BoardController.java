package com.topic.controller;

import com.topic.controller.helpers.BoardControllerHelper;
import com.topic.dto.api.request.BoardCreateRequest;
import com.topic.dto.api.response.BoardFullInfoResponse;
import com.topic.dto.api.response.BoardPaginatedResponse;
import com.topic.dto.api.response.EntityIdResponse;
import com.topic.service.BoardService;
import com.topic.service.dto.BoardDto;
import com.topic.service.dto.BoardWithAllPublicationsDto;
import com.topic.service.dto.PaginatedBoardDto;
import com.topic.util.annotations.Logging;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @PostMapping()
    @Logging
    public EntityIdResponse createBoard(
            @Valid @RequestBody BoardCreateRequest request
    ) {
        BoardDto board = boardService.createBoard(BoardControllerHelper.mapToCreateBoardDTO(request));
        return new EntityIdResponse(board.id());
    }


    @GetMapping("/{id}")
    @Logging
    public BoardFullInfoResponse getBoardFullInfo(
            @PathVariable Long id
    ) {
        BoardWithAllPublicationsDto data = boardService.getBoardWithAllPublications(id);
        return BoardControllerHelper.mapToBoardFullInfoResponse(data);
    }


    @GetMapping("")
    @Logging
    public BoardPaginatedResponse getBoardsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        PaginatedBoardDto paginatedBoards = boardService.getBoards(page, pageSize);
        return BoardControllerHelper.mapToBoardPaginatedResponse(paginatedBoards);
    }
}