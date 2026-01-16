package com.topic.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtTokenService {

    String generateAccessToken(String login,  Map<String, Object> claims);

    String generateAccessToken(String login);

    String generateRefreshToken(String login);

    Claims validateAndParseToken(String token);

    String getUsernameFromToken(String token);

    boolean isTokenExpired(String token);

    boolean isRefreshToken(String token);

    String refreshAccessToken(String refreshToken);
}
