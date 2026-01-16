package com.topic.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePublicationDto(
        @NotBlank String content,

        @NotNull @Positive Long boardId,

        @Positive Long userId

) {}