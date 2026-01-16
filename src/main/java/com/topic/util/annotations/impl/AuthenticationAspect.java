package com.topic.util.annotations.impl;

import com.topic.service.JwtTokenService;
import com.topic.service.UserService;
import com.topic.service.dto.UserDto;
import com.topic.util.exeptions.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Aspect
@Component
@Slf4j
public class AuthenticationAspect {

    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    public AuthenticationAspect(
            JwtTokenService jwtTokenService,
            UserService userService
    ) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    @Around("@annotation(com.topic.util.annotations.Authenticated)")
    public Object authenticate(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = getCurrentRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing or invalid Authorization header");
        }

        try {
            String token = authHeader.substring(7);
            String login = jwtTokenService.getUsernameFromToken(token);

            UserDto userDto = userService.getUserByLogin(login)
                    .orElseThrow(() -> new UnauthorizedException("User not found"));

            request.setAttribute("currentUser", userDto);

            return joinPoint.proceed();

        } catch (JwtException | AuthenticationException e) {
            throw new UnauthorizedException("Invalid token", e);
        }
    }

    private HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("No request context");
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

}
