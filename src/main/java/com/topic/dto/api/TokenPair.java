package com.topic.dto.api;

public record TokenPair(
     String accessToken,
     String refreshToken
) { }