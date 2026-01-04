package com.topic.dto.api.request;

import jakarta.validation.constraints.NotBlank;

public record BoardCreateRequest(
        @NotBlank String title
) { }
