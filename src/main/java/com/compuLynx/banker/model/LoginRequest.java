package com.compuLynx.banker.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Login Request Entity
 * @Params customer_id, customer_pin
 */
@Getter
@Builder
public class LoginRequest {
    private final String customer_id;
    private final String customer_pin;
}