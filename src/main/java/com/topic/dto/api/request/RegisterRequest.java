package com.topic.dto.api.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank String name,
    @NotBlank String login,
    @NotBlank String password
) { }
