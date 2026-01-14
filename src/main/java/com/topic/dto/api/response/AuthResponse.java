package com.topic.dto.api.response;

import com.topic.dto.api.TokenPair;
import com.topic.entity.main.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String name;
    private String accessToken;
    private String refreshToken;

    public AuthResponse(User user, TokenPair tokens){
        this.userId = user.getId();
        this.name = user.getUsername();
        this.accessToken = tokens.accessToken();
        this.refreshToken = tokens.refreshToken();
    }
}
