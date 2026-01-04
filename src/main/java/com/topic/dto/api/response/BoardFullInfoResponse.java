package com.topic.dto.api.response;

import java.util.List;

public record BoardFullInfoResponse(
        BoardMainInfoResponse info,
        List<PublicationInfoResponse> publications,
        PageResponse pages
) { }