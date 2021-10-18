package com.sid.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

/**
 * This class defines rest exceptions attributes and constructors
 */
public class RestException extends RuntimeException {

    private final @Getter
    HttpStatus httpStatus;

    public RestException(@NotNull String message, @NotNull HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public RestException(@NotNull String message, @NotNull HttpStatus status, @NotNull Throwable throwable) {
        super(message, throwable);
        this.httpStatus = status;
    }

    public RestException(@NotNull String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public RestException(@NotNull String message, @NotNull Throwable throwable) {
        super(message, throwable);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public RestException(@NotNull Throwable throwable) {
        super(throwable.getMessage(), throwable);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
