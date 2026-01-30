package com.ecommerce.ecommerce.common.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
public record ApiError(
        Instant timestamp,
        HttpStatus status,
        String message,
        String path
){}
