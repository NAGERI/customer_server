package com.compuLynx.banker.service;

import com.compuLynx.banker.security.JwtIssuer;
import com.compuLynx.banker.security.UserPrincipal;
import com.compuLynx.banker.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    /**
     * To Be implemented.
     *
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public LoginResponse attemptLogin(String customer_id, String customer_pin) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customer_id, customer_pin)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .customer_pin(principal.getCustomer_pin())
                .customer_id(principal.getCustomer_id())
                .build());

        return LoginResponse.builder()
                .token(token)
                .build();
    }*/
}