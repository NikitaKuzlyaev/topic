package com.topic.service.helpers;

import com.topic.entity.main.Publication;
import com.topic.service.dto.PublicationDto;

public class PublicationServiceImplHelper {
    public static PublicationDto mapToPublicationDto(Publication data) {
        return new PublicationDto(data.getId(), data.getAuthor().getUsername(), data.getContent());
    }
}
