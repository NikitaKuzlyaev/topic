package com.topic.controller;

import com.topic.controller.helpers.PublicationControllerHelper;
import com.topic.dto.api.request.PublicationCreateRequest;
import com.topic.dto.api.response.EntityIdResponse;
import com.topic.dto.api.response.PublicationInfoResponse;
import com.topic.service.PublicationService;
import com.topic.service.dto.PublicationDto;
import com.topic.service.dto.UserDto;
import com.topic.util.annotations.Authenticated;
import com.topic.util.annotations.LoggingToKafkaTopic;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/publication")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping()
    @Authenticated
    @LoggingToKafkaTopic
    public EntityIdResponse createPublication(
            @Valid @RequestBody PublicationCreateRequest request,
            HttpServletRequest req
    ) {
        UserDto userDto = (UserDto) req.getAttribute("currentUser");

        PublicationDto publication = publicationService.createPublication(
                PublicationControllerHelper.mapToCreatePublicationDto(request, userDto.id())
        );

        return new EntityIdResponse(publication.id());
    }

    @GetMapping("/{id}")
    @LoggingToKafkaTopic
    public PublicationInfoResponse getPublication(
            @PathVariable Long id
    ) {
        // TODO: заглушка
        return new PublicationInfoResponse(123L, "Author", "Content");
    }

}