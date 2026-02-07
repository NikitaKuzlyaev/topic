package com.topic.service.impl;

import com.topic.dto.api.request.LoginRequest;
import com.topic.dto.api.request.RegisterRequest;
import com.topic.service.AuthenticationService;
import com.topic.service.UserService;
import com.topic.service.dto.UserCreateDto;
import com.topic.service.dto.UserDto;
import com.topic.util.annotations.LoggingToSystemOut;
import com.topic.util.exeptions.EntityAlreadyExistsException;
import com.topic.util.exeptions.EntityDoesNotExistsException;
import com.topic.util.exeptions.NotImplementedException;
import com.topic.util.exeptions.PasswordValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(
            UserService userService
    ) {
        this.userService = userService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    @LoggingToSystemOut
    public UserDto registerUser(RegisterRequest request) {
        Optional<UserDto> data = userService.getUserByLogin(request.login());
        if (data.isPresent()){
            throw new EntityAlreadyExistsException("User with such login already exists");
        }

        String passwordWithSaltHash = passwordEncoder.encode(request.password());

        UserCreateDto userCreateDto =  new UserCreateDto(
                request.username(), request.login(), passwordWithSaltHash
        );

        return userService.createUser(userCreateDto);
    }

    @Override
    @LoggingToSystemOut
    public UserDto loginUser(LoginRequest request) {

        Optional<UserDto> data = userService.getUserByLogin(request.login());
        if (data.isEmpty()){
            throw new EntityDoesNotExistsException("User with such login doesn't exists");
        }

        UserDto userDto = data.get();

        if (!verifyPassword(request.password(), userDto.hashedPassword())){
            throw new PasswordValidationException("Wrong password for user with such login");
        }

        return userDto;
    }

    @Override
    public boolean verifyPassword(String password, String storedHash) {
        return passwordEncoder.matches(password, storedHash);
    }

    @Override
    public void logoutUser() {
        throw new NotImplementedException("");
    }

    @Override
    public void blockToken() {
        throw new NotImplementedException("");
    }
}
