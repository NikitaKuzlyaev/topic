package com.topic.service.dto;

import jakarta.validation.constraints.Positive;

public record DeleteBoardDto(
        @Positive Long boardId
) { }
