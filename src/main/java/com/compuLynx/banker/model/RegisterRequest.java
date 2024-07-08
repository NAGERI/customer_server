package com.compuLynx.banker.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequest {
    private final String customer_id;
    private final String email;
    private final String name;
}
