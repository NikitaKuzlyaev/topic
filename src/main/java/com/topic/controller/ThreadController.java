package com.topic.controller;

import com.topic.dto.api.request.ThreadCreateRequest;
import com.topic.dto.api.request.ThreadMainInfoRequest;
import com.topic.dto.api.response.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/thread")
public class ThreadController {

    @PostMapping()
    public EntityIdResponse createThread(
            @Valid @RequestBody ThreadCreateRequest request
    ) {
        // TODO: заглушка
        return new EntityIdResponse(123L);
    }

    @GetMapping("/{id}")
    public ThreadMainInfoResponse getThreadMainInfo(
            @PathVariable Long id
    ) {
        // TODO: заглушка
        return new ThreadMainInfoResponse(123L, "Name");
    }

    @GetMapping("/{id}/full")
    public ThreadFullInfoResponse getThreadFullInfo(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int pageSize
    ) {
        // TODO: заглушка
        return new ThreadFullInfoResponse(
                new ThreadMainInfoResponse(123L, "Name"),
                new ArrayList<>(),
                new PageResponse(1, 10, 5)
        );
    }
}