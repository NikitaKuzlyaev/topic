package com.topic.service.dto;

public record BoardWithAllPublicationsDto(
    BoardDto board,
    PublicationsListDto publications
) {}
