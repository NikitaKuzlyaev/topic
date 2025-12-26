package com.topic.dto.api.request;

import jakarta.validation.constraints.NotBlank;

public record ThreadCreateRequest(
        @NotBlank String name
) { }
