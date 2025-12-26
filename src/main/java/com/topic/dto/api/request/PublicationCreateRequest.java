package com.topic.dto.api.request;

import jakarta.validation.constraints.NotBlank;

public record PublicationCreateRequest(
        @NotBlank String author,
        @NotBlank String content
) { }
