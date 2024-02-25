package com.movie.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthFieldException.class)
    public ResponseEntity<ErrorMessageDto> authFieldExceptionHandler (AuthFieldException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageDto);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorMessageDto> loginExceptionHandler (LoginException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageDto);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RegisterException.class)
    public ResponseEntity<ErrorMessageDto> registerExceptionHandler (RegisterException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageDto);
    }

}
