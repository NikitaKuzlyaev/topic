package com.topic.service;

import com.topic.service.dto.UserCreateDto;
import com.topic.service.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserDto createUser(UserCreateDto data);

    Optional<UserDto> getUserById(Long userId);

    Optional<UserDto> getUserByLogin(String login);

}
