package com.topic.service;


import com.topic.service.dto.CreatePublicationDto;
import com.topic.service.dto.PublicationDto;
import jakarta.persistence.EntityExistsException;

public interface PublicationService {
    PublicationDto createPublication(CreatePublicationDto data);

    PublicationDto getPublication(Long publicationId) throws EntityExistsException;

    // пока не понимаю как отдавать публикации - пусть пока отдаются все что есть
    //PaginatedPublicationDto getBoards(int page, int pageSize);
}
