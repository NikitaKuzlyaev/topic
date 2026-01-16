package com.topic.controller;

import com.topic.dto.api.TokenPair;
import com.topic.dto.api.request.LoginRequest;
import com.topic.dto.api.request.RegisterRequest;
import com.topic.dto.api.response.AuthResponse;
import com.topic.dto.api.response.UserDataResponse;
import com.topic.service.AuthenticationService;
import com.topic.service.JwtTokenService;
import com.topic.service.UserService;
import com.topic.service.dto.UserDto;
import com.topic.util.annotations.Logging;
import com.topic.util.exeptions.EntityDoesNotExistsException;
import com.topic.util.exeptions.PasswordValidationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    public AuthController(
            AuthenticationService authenticationService,
            JwtTokenService jwtTokenService,
            UserService userService
    ) {
        this.authenticationService = authenticationService;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
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
    @Logging
    public ResponseEntity<UserDataResponse> me(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        try{
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String token = authHeader.substring(7); // удаляем "Bearer "

            String login = jwtTokenService.getUsernameFromToken(token);

            Optional<UserDto> data = userService.getUserByLogin(login);
            if (data.isEmpty()){
                throw new RuntimeException(); // todo: пока так, потом придумать как лучше
            }
            UserDto userDto = data.get();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new UserDataResponse(userDto.id(), userDto.name(), userDto.login()));

        } catch (Exception e){ // todo: указать конкретные исключения
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
