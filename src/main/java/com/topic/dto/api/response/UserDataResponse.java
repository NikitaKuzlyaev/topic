package com.topic.dto.api.response;

public record UserDataResponse(
        Long userId,
        String name,
        String login
) { }
