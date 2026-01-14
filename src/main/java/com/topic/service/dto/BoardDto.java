package com.topic.service.dto;

import com.topic.entity.main.Board;
import com.topic.entity.main.User;

import java.time.LocalDateTime;

public record BoardDto(
    Long id,
    String title,
    User author,
    Board parent,
    LocalDateTime createdAt
) { }
