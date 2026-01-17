package com.topic.dto.api.request;

import jakarta.validation.constraints.Positive;

public record BoardDeleteRequest(
        @Positive
        Long boardId
) { }