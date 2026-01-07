package com.topic.dto.api.response;


import java.util.List;

public record BoardFullInfoResponse(
        BoardMainInfoResponse boardInfo,
        List<PublicationInfoResponse> publications
) { }