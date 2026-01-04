package com.topic.dto.api.response;

import java.util.List;

public record BoardPaginatedResponse(
        PageResponse pageInfo,
        List<BoardMainInfoResponse> threads
) { }
