package com.topic.service.dto;

public record UserDto(
    Long id,
    String name,
    String login,
    String hashedPassword
) { }
