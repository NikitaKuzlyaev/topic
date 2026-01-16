package com.topic.service;

import com.topic.dto.api.request.LoginRequest;
import com.topic.dto.api.request.RegisterRequest;
import com.topic.service.dto.UserDto;

public interface AuthenticationService {

    // todo: используется то же самое dto что в слое контроллера
    // они абсолютно одинаковые, но мне так лень делать повторки - скорее всего это даже не пригодится
    UserDto registerUser(RegisterRequest request);

    UserDto loginUser(LoginRequest request);

    boolean verifyPassword(String password, String storedHash);

    void logoutUser();

    void blockToken();

}
