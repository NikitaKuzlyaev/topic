package com.topic.service.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateBoardDto(
        @NotBlank String title
) { }
