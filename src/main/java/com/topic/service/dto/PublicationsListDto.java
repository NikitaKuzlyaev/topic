package com.topic.service.dto;

import java.util.List;

public record PublicationsListDto(
    List<PublicationDto> publications
) { }
