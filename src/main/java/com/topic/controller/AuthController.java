package com.topic.controller;

import com.topic.dto.api.TokenPair;
import com.topic.dto.api.request.LoginRequest;
import com.topic.dto.api.request.RegisterRequest;
import com.topic.dto.api.response.AuthResponse;
import com.topic.dto.api.response.UserDataResponse;
import com.topic.service.AuthenticationService;
import com.topic.service.JwtTokenService;
import com.topic.service.dto.UserDto;
import com.topic.util.annotations.Authenticated;
import com.topic.util.annotations.Logging;
import com.topic.util.exeptions.EntityDoesNotExistsException;
import com.topic.util.exeptions.PasswordValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;

    public AuthController(
            AuthenticationService authenticationService,
            JwtTokenService jwtTokenService
    ) {
        this.authenticationService = authenticationService;
        this.jwtTokenService = jwtTokenService;
    }

    // todo: я не очень понял как я тут решил использовать AuthResponse вместе с токенами (хотя они не нужны)
    // по сути тут только 201 надо кидать, если все ок. пока пусть так...
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ){
        // todo: завернуть в try или сделать отдельный обработчик ошибок чтобы не оборачивать каждый раз
        UserDto userDto = authenticationService.registerUser(request);

        String accessToken = jwtTokenService.generateAccessToken(userDto.login());
        String refreshToken = jwtTokenService.generateRefreshToken(userDto.login());
        TokenPair tokens = new TokenPair(accessToken, refreshToken);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(userDto, tokens));
    }

    @PostMapping("/login")
    @Logging
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        try {
            UserDto userDto = authenticationService.loginUser(request);

            String accessToken = jwtTokenService.generateAccessToken(userDto.login());
            String refreshToken = jwtTokenService.generateRefreshToken(userDto.login());
            TokenPair tokens = new TokenPair(accessToken, refreshToken);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AuthResponse(userDto, tokens));

        } catch (EntityDoesNotExistsException | PasswordValidationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/me")
    @Authenticated
    @Logging
    public ResponseEntity<UserDataResponse> me(
            HttpServletRequest request
    ) {
        UserDto userDto = (UserDto) request.getAttribute("currentUser");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserDataResponse(userDto.id(), userDto.name(), userDto.login()));

    }

    @PostMapping("/refresh")
    public void refresh(){

    }

    @PostMapping("/logout")
    public void logout(){

    }
}
