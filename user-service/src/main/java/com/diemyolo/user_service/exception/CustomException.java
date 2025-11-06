package com.diemyolo.user_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final Serializable data;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.data = null;
    }

    public CustomException(String message, Serializable data, HttpStatus status) {
        super(message);
        this.status = status;
        this.data = data;
    }
}
