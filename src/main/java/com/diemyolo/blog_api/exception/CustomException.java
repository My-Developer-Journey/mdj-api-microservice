package com.diemyolo.blog_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final Object data; // ✅ chứa lỗi chi tiết (Map/List...)

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.data = null;
    }

    public CustomException(String message, Object data, HttpStatus status) {
        super(message);
        this.status = status;
        this.data = data;
    }
}
