package com.topic.dto.api.request;

import jakarta.validation.constraints.NotBlank;

public record AccessTokenRequest(
    @NotBlank(message = "access token is required")
    String accessToken
) { }
