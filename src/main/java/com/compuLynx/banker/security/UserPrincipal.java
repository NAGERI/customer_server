package com.compuLynx.banker.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class UserPrincipal implements UserDetails {
    private final Long userId;
    private final String email;
    private final String customer_id;

    @JsonIgnore
    private final String customer_pin;

    private final List<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return customer_pin;
    }

    @Override
    public String getUsername() { return customer_id; }

    @Override
    public boolean isAccountNonExpired() {
        return true; // <-- Very important to not forget
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // <-- Very important to not forget
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // <-- Very important to not forget
    }

    @Override
    public boolean isEnabled() {
        return true; // <-- Very important to not forget
    }
}