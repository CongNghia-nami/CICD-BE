package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties jwtProperties;

    public String issue(Request request){
        return JWT.create()
                .withSubject(String.valueOf(request.getUserId()))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("username", request.getUsername())
                .withClaim("au", request.getRoles())

                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));

    }

    @Getter
    @Builder
    public static class Request{
        private final Long userId;
        private final String username;
        private final List<String> roles;
    }
    public String issueRefreshToken(JwtIssuer.Request request) {
        return JWT.create()
                .withSubject(String.valueOf(request.getUserId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration())) // Thời gian tồn tại của refresh token
                .withClaim("username", request.getUsername())
                .withClaim("au", request.getRoles())
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
