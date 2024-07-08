package com.compuLynx.banker.security;

import com.compuLynx.banker.repo.CustomerRepository;
import com.compuLynx.banker.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String customer_id) throws UsernameNotFoundException {
        var user = customerService.findByCustomerId(customer_id).orElseThrow();
        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .customer_pin(user.getCustomer_pin())
                .build();
    }
}