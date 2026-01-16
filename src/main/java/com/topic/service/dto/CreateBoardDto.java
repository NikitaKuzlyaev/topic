package com.topic.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateBoardDto(
        @NotBlank String title,

        @Positive Long userId
) { }
