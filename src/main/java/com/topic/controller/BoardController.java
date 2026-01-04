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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/thread")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping()
    public EntityIdResponse createThread(
            @Valid @RequestBody BoardCreateRequest request
    ) {
        BoardDto board = boardService.createBoard(Util.mapToCreateThreadDTO(request));
        return new EntityIdResponse(board.id());
    }

    @GetMapping("/{id}")
    public BoardMainInfoResponse getBoard(
            @PathVariable Long id
    ) {
        BoardDto board = boardService.getBoard(id);
        return new BoardMainInfoResponse(board.id(), board.title());
    }

    @GetMapping("")
    public BoardPaginatedResponse getBoardsPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int pageSize
    ){
        PaginatedBoardDto paginatedBoards = boardService.getBoards(page, pageSize);
        return Util.mapToBoardPaginatedResponse(paginatedBoards);
    }

}

class Util {
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

}