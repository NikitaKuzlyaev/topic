package com.topic.service.impl;

import com.topic.config.JwtConfig;
import com.topic.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtConfig jwtConfig;

    public JwtTokenServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    @Override
    public String generateAccessToken(String username, Map<String, Object> claims) {
        Map<String, Object> tokenClaims = new HashMap<>(claims);
        tokenClaims.put("tokenType", "ACCESS");

        return Jwts.builder()
                .setSubject(username)
                .addClaims(tokenClaims)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(
                        Instant.now().plusMillis(jwtConfig.getAccessTokenExpiration())
                ))
                .setId(UUID.randomUUID().toString())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("tokenType", "REFRESH")
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(
                        Instant.now().plusMillis(jwtConfig.getRefreshTokenExpiration())
                ))
                .setId(UUID.randomUUID().toString())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Claims validateAndParseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return validateAndParseToken(token).getSubject();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return validateAndParseToken(token)
                .getExpiration()
                .before(new Date());
    }

    @Override
    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(validateAndParseToken(token).get("tokenType", String.class));
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!isRefreshToken(refreshToken)) {
            throw new SecurityException("Invalid refresh token");
        }

        if (isTokenExpired(refreshToken)) {
            throw new SecurityException("Refresh token expired");
        }

        Claims claims = validateAndParseToken(refreshToken);
        String username = claims.getSubject();

        Map<String, Object> accessTokenClaims = new HashMap<>();
        if (claims.containsKey("roles")) {
            accessTokenClaims.put("roles", claims.get("roles"));
        }
        if (claims.containsKey("userId")) {
            accessTokenClaims.put("userId", claims.get("userId"));
        }

        return generateAccessToken(username, accessTokenClaims);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
