package com.compuLynx.banker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class LoginResponse {
    private String token;
}