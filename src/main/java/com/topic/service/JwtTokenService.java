package com.topic.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtTokenService {

    String generateAccessToken(String username,  Map<String, Object> claims);

    String generateRefreshToken(String username);

    Claims validateAndParseToken(String token);

    String getUsernameFromToken(String token);

    boolean isTokenExpired(String token);

    boolean isRefreshToken(String token);

    String refreshAccessToken(String refreshToken);
}
