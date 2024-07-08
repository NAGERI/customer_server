package com.compuLynx.banker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(Request request) {
        var now = Instant.now();

        return JWT.create()
                .withSubject(String.valueOf("BakingDemoApp"))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(properties.getTokenDuration()))
                .withClaim("pin", request.getCustomer_pin())
                .withClaim("id", request.getCustomer_id())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }


    /**
     * @Params userId, email, customer_id and customer_pin
     */

    @Getter
    @Builder
    public static class Request {
        private final String customer_id;
        private final String customer_pin;
    }
}