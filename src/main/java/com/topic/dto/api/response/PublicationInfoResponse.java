package com.topic.dto.api.response;

public record PublicationInfoResponse(
        Long id,
        String author,
        String content
) { }