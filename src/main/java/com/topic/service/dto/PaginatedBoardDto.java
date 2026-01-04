package com.topic.service.dto;

public record PaginatedBoardDto(
    int currentPage,
    int totalPages,
    int pageSize,
    Iterable<BoardDto> threads
) {}
