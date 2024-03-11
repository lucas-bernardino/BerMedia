package com.movie.server.exception;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;

public class ErrorMessageDto {

    @JsonView(ExceptionView.ViewDefault.class)
    private HttpStatus httpStatus;
    @JsonView(ExceptionView.ViewDefault.class)
    private String message;
    @JsonView(ExceptionView.ViewThrowable.class)
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
