package com.topic.service.impl;

import com.topic.dto.api.request.RegisterRequest;
import com.topic.service.AuthenticationService;
import com.topic.service.UserService;
import com.topic.service.dto.UserCreateDto;
import com.topic.service.dto.UserDto;
import com.topic.util.exeptions.EntityAlreadyExists;
import com.topic.util.exeptions.NotImplementedException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
    public UserDto registerUser(RegisterRequest request) {
        Optional<UserDto> data = userService.getUserByLogin(request.login());
        if (data.isPresent()){
            throw new EntityAlreadyExists("");
        }

        String passwordWithSaltHash = passwordEncoder.encode(request.password());

        UserCreateDto userCreateDto =  new UserCreateDto(
                request.name(), request.login(), passwordWithSaltHash
        );
        return userService.createUser(userCreateDto);
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
    public void generateAccessToken() {
        throw new NotImplementedException("");
    }

    @Override
    public void generateRefreshToken() {
        throw new NotImplementedException("");
    }

    @Override
    public void validateAccessToken() {
        throw new NotImplementedException("");
    }

    @Override
    public void validateRefreshToken() {
        throw new NotImplementedException("");
    }

    @Override
    public void blockToken() {
        throw new NotImplementedException("");
    }

    private String generatePasswordHash(String password) {
        return passwordEncoder.encode(password);
    }
}
