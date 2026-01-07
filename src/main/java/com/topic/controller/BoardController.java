package com.topic.controller;

import com.topic.dto.api.request.BoardCreateRequest;
import com.topic.dto.api.response.*;
import com.topic.service.BoardService;
import com.topic.service.dto.*;
import com.topic.util.annotations.Logging;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/thread")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping()
    @Logging
    public EntityIdResponse createThread(
            @Valid @RequestBody BoardCreateRequest request
    ) {
        BoardDto board = boardService.createBoard(BoardControllerUtil.mapToCreateThreadDTO(request));
        return new EntityIdResponse(board.id());
    }

    @Deprecated // какой deprecated? - проекту 2 дня
    @GetMapping("/{id}/old")
    @Logging
    public BoardMainInfoResponse getBoard(
            @PathVariable Long id
    ) {
        BoardDto board = boardService.getBoard(id);
        return new BoardMainInfoResponse(board.id(), board.title());
    }

    @GetMapping("/{id}")
    @Logging
    public BoardFullInfoResponse getBoardFullInfo(
            @PathVariable Long id
    ) {
        BoardWithAllPublicationsDto data = boardService.getBoardWithAllPublications(id);
        return BoardControllerUtil.mapToBoardFullInfoResponse(data);
    }


    @GetMapping("")
    @Logging
    public BoardPaginatedResponse getBoardsPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int pageSize
    ){
        PaginatedBoardDto paginatedBoards = boardService.getBoards(page, pageSize);
        return BoardControllerUtil.mapToBoardPaginatedResponse(paginatedBoards);
    }

}

// todo: move in better place
class BoardControllerUtil {
    public static CreateBoardDto mapToCreateThreadDTO(BoardCreateRequest data) {
        return new CreateBoardDto(data.title());
    }

    public static BoardPaginatedResponse mapToBoardPaginatedResponse(PaginatedBoardDto data){
        // todo: rename
        List<BoardMainInfoResponse> res = new ArrayList<>();
        for (var el: data.threads()){
            res.add(new BoardMainInfoResponse(el.id(), el.title()));
        }

        return new BoardPaginatedResponse(
                new PageResponse(data.currentPage(), data.pageSize(), data.totalPages()), res);
    }

    // todo: it's too silly...
    public static BoardFullInfoResponse mapToBoardFullInfoResponse(BoardWithAllPublicationsDto data){
        return new BoardFullInfoResponse(
                new BoardMainInfoResponse(data.board().id(), data.board().title()),
                data.publications().publications().stream().map(BoardControllerUtil::mapToPublicationInfoResponse).toList()
        );
    }

    public static PublicationInfoResponse mapToPublicationInfoResponse(PublicationDto data){
        return new PublicationInfoResponse(data.id(), data.author(), data.content());
    }



}