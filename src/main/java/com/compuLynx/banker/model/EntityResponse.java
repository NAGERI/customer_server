package com.compuLynx.banker.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EntityResponse {
    Object entity;
    String status;
}
