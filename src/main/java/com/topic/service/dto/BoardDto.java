package com.topic.service.dto;

import com.topic.entity.Board;
import com.topic.entity.User;

import java.time.LocalDateTime;

public record BoardDto(
    Long id,
    String title,
    User author,
    Board parent,
    LocalDateTime createdAt
) { }
