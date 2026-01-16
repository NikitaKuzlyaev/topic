package com.topic.controller.helpers;

import com.topic.dto.api.request.PublicationCreateRequest;
import com.topic.service.dto.CreatePublicationDto;

public class PublicationControllerHelper {
    public static CreatePublicationDto mapToCreatePublicationDto(PublicationCreateRequest data) {
        return new CreatePublicationDto(data.content(), data.boardId());
    }
}
