package com.topic.dto.api.response;

import com.topic.dto.api.TokenPair;
import com.topic.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String name;
    private String accessToken;
    private String refreshToken;

    public AuthResponse(UserDto userDto, TokenPair tokens){
        this.userId = userDto.id();
        this.name = userDto.name();
        this.accessToken = tokens.accessToken();
        this.refreshToken = tokens.refreshToken();
    }
}
