package com.topic.controller;

import com.topic.dto.api.request.BoardCreateRequest;
import com.topic.dto.api.response.*;
import com.topic.service.BoardService;
import com.topic.service.dto.CreateBoardDto;
import com.topic.service.dto.PaginatedBoardDto;
import com.topic.service.dto.BoardDto;
import com.topic.util.exeptions.NotImplementedException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/thread")
public class BoardController {

    private final BoardService threadService;

    @Autowired
    public BoardController(BoardService threadService) {
        this.threadService = threadService;
    }

    @PostMapping()
    public EntityIdResponse createThread(
            @Valid @RequestBody BoardCreateRequest request
    ) {
        BoardDto thread = threadService.createBoard(Util.mapToCreateThreadDTO(request));
        return new EntityIdResponse(thread.id());
    }

    @GetMapping("/{id}")
    public BoardMainInfoResponse getThread(
            @PathVariable Long id
    ) {
        BoardDto thread = threadService.getBoard(id);
        return new BoardMainInfoResponse(thread.id(), thread.title());
    }

    @GetMapping("/p")
    public BoardPaginatedResponse getThreadsPaginated(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "20") int pageSize
    ){
        PaginatedBoardDto paginatedThreads = threadService.getBoards(page, pageSize);
        return Util.mapToThreadsPaginatedResponse(paginatedThreads);
    }

}

class Util {
    public static CreateBoardDto mapToCreateThreadDTO(BoardCreateRequest data) {
        return new CreateBoardDto(data.title());
    }

    public static BoardPaginatedResponse mapToThreadsPaginatedResponse(PaginatedBoardDto data){
        // TODO
        throw new NotImplementedException("ThreadController");
    }

}