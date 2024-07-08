package com.compuLynx.banker.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt) {

        return UserPrincipal.builder()
                .customer_pin(jwt.getClaim("pin").asString())
                .customer_id(jwt.getClaim("id").asString())
                .build();
    }


}