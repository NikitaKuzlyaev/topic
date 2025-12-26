package com.topic.controller;

import com.topic.dto.api.request.PublicationCreateRequest;
import com.topic.dto.api.response.EntityIdResponse;
import com.topic.dto.api.response.PublicationInfoResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/publication")
public class PublicationController {

    @PostMapping()
    public EntityIdResponse createPublication(
            @Valid @RequestBody PublicationCreateRequest request
    ) {
        // TODO: заглушка
        return new EntityIdResponse(123L);
    }

    @GetMapping("/{id}")
    public PublicationInfoResponse getPublication(
            @PathVariable Long id
    ) {
        // TODO: заглушка
        return new PublicationInfoResponse(123L, "Author", "Content");
    }

}