package com.topic.dto.api.response;

public record PageResponse(
        int currentPage,
        int pageSize,
        int totalPages
) { }
