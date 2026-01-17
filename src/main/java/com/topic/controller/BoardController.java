package com.topic.controller;

import com.topic.controller.helpers.BoardControllerHelper;
import com.topic.dto.api.request.BoardCreateRequest;
import com.topic.dto.api.response.BoardFullInfoResponse;
import com.topic.dto.api.response.BoardPaginatedResponse;
import com.topic.dto.api.response.EntityIdResponse;
import com.topic.dto.api.response.SuccessResponse;
import com.topic.entity.ActionType;
import com.topic.entity.ResourceType;
import com.topic.service.BoardPolicyService;
import com.topic.service.BoardService;
import com.topic.service.dto.BoardDto;
import com.topic.service.dto.BoardWithAllPublicationsDto;
import com.topic.service.dto.PaginatedBoardDto;
import com.topic.service.dto.UserDto;
import com.topic.util.annotations.Authenticated;
import com.topic.util.annotations.Logging;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardPolicyService boardPolicyService;

    @Autowired
    public BoardController(
            BoardService boardService,
            BoardPolicyService boardPolicyService
    ) {
        this.boardService = boardService;
        this.boardPolicyService = boardPolicyService;
    }


    @PostMapping()
    @Authenticated
    @Logging
    public EntityIdResponse createBoard(
            @Valid @RequestBody BoardCreateRequest request,
            HttpServletRequest req
    ) {
        UserDto userDto = (UserDto) req.getAttribute("currentUser");

        BoardDto board = boardService.createBoard(BoardControllerHelper.mapToCreateBoardDTO(request, userDto.id()));
        return new EntityIdResponse(board.id());
    }

    @DeleteMapping("/{boardId}")
    @Authenticated
    @Logging
    public SuccessResponse deleteBoard(
            @PathVariable Long boardId,
            HttpServletRequest req
    ) {
        UserDto userDto = (UserDto) req.getAttribute("currentUser");

        boardPolicyService
                .getPermission(userDto.id(), boardId, ActionType.DELETE, ResourceType.BOARD)
                .failIfDenied(() -> new AccessDeniedException("User doesn't has permissions for this operation"));

        boardService.deleteBoard(BoardControllerHelper.mapToDeleteBoardDTO(boardId));
        return new SuccessResponse();
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
            @RequestParam(required = false) Long parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        PaginatedBoardDto paginatedBoards = boardService.getBoards(page, pageSize, parentId);
        return BoardControllerHelper.mapToBoardPaginatedResponse(paginatedBoards);
    }
}