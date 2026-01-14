package com.topic.service.dto;

public record UserCreateDto(
    String username,
    String login,
    String hashedPasswordAndSalt
) { }