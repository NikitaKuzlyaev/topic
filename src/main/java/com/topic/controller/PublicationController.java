package com.topic.controller;

import com.topic.dto.api.request.PublicationCreateRequest;
import com.topic.dto.api.response.*;
import com.topic.service.PublicationService;
import com.topic.service.dto.CreatePublicationDto;
import com.topic.service.dto.PublicationDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/publication")
public class PublicationController {

    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService){
        this.publicationService = publicationService;
    }

    @PostMapping()
    public EntityIdResponse createPublication(
            @Valid @RequestBody PublicationCreateRequest request
    ) {
        PublicationDto publication = publicationService.createPublication(
                PublicationUtil.mapToCreatePublicationDto(request)
        );

        return new EntityIdResponse(publication.id());
    }

    @GetMapping("/{id}")
    public PublicationInfoResponse getPublication(
            @PathVariable Long id
    ) {
        // TODO: заглушка
        return new PublicationInfoResponse(123L, "Author", "Content");
    }

}

// todo: move in better place
class PublicationUtil {
    public static CreatePublicationDto mapToCreatePublicationDto(PublicationCreateRequest data) {
        return new CreatePublicationDto(data.content(), data.boardId());
    }
}