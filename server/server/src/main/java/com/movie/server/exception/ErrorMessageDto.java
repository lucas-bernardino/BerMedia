package com.movie.server.exception;

import org.springframework.http.HttpStatus;

public record ErrorMessageDto(HttpStatus httpStatus,
                              String message) {
}
