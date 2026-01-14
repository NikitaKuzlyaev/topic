package com.topic.controller;

import com.topic.dto.api.TokenPair;
import com.topic.dto.api.request.RegisterRequest;
import com.topic.dto.api.response.AuthResponse;
import com.topic.entity.main.User;
import com.topic.service.AuthenticationService;
import com.topic.service.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ){

        UserDto userDto = authenticationService.registerUser(request);


        User user = new User(); // заглушка
        TokenPair tokens = new TokenPair("a", "r"); // заглушка

        return ResponseEntity
                .status(HttpStatus.CREATED)
//                .header(HttpHeaders.AUTHORIZATION, tokens.getAccessToken())
//                .header("X-Refresh-Token", tokens.getRefreshToken())
                .body(new AuthResponse(user, tokens));
    }

    @PostMapping("/login")
    public void login(){

    }

    @PostMapping("/refresh")
    public void refresh(){

    }

    @PostMapping("/logout")
    public void logout(){

    }
}
