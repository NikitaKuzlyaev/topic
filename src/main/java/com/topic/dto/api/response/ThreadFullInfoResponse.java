package com.topic.dto.api.response;

import java.util.List;

public record ThreadFullInfoResponse(
        ThreadMainInfoResponse info,
        List<PublicationInfoResponse> publications,
        PageResponse pages
) { }