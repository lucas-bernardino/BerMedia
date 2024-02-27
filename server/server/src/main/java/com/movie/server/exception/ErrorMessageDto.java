package com.movie.server.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessageDto {

    private HttpStatus httpStatus;
    private String message;
    private Throwable throwable;

    public ErrorMessageDto(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ErrorMessageDto(HttpStatus httpStatus, String message, Throwable throwable) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.throwable = throwable;
    }
}
