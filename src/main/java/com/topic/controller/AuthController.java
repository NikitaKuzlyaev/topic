package com.topic.controller;

import com.topic.dto.api.TokenPair;
import com.topic.dto.api.request.LoginRequest;
import com.topic.dto.api.request.RegisterRequest;
import com.topic.dto.api.response.AuthResponse;
import com.topic.service.AuthenticationService;
import com.topic.service.JwtTokenService;
import com.topic.service.dto.UserDto;
import com.topic.util.annotations.Logging;
import com.topic.util.exeptions.EntityDoesNotExistsException;
import com.topic.util.exeptions.PasswordValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestBody RegisterRequest request
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
            @RequestBody LoginRequest request
    ) {
        log.info(request.login() + " " + request.password()); //todo: remove after debug
        try {
            UserDto userDto = authenticationService.loginUser(request);

            String accessToken = jwtTokenService.generateAccessToken(userDto.login());
            String refreshToken = jwtTokenService.generateRefreshToken(userDto.login());
            TokenPair tokens = new TokenPair(accessToken, refreshToken);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AuthResponse(userDto, tokens));

        } catch (EntityDoesNotExistsException | PasswordValidationException e) {
            log.error(String.valueOf(e.fillInStackTrace())); //todo: remove after debug
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh")
    public void refresh(){

    }

    @PostMapping("/logout")
    public void logout(){

    }
}
