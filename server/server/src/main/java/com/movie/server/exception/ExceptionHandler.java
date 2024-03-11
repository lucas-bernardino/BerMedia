package com.movie.server.exception;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @JsonView(ExceptionView.ViewDefault.class)
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthFieldException.class)
    public ResponseEntity<ErrorMessageDto> authFieldExceptionHandler (AuthFieldException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageDto);
    }

    @JsonView(ExceptionView.ViewDefault.class)
    @org.springframework.web.bind.annotation.ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorMessageDto> loginExceptionHandler (LoginException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageDto);
    }

    @JsonView(ExceptionView.ViewDefault.class)
    @org.springframework.web.bind.annotation.ExceptionHandler(RegisterException.class)
    public ResponseEntity<ErrorMessageDto> registerExceptionHandler (RegisterException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageDto);
    }

    @JsonView(ExceptionView.ViewDefault.class)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageDto> objectNotFoundExceptionHandler(NotFoundException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageDto);
    }

    @JsonView(ExceptionView.ViewDefault.class)
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageDto> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageDto);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ErrorMessageDto> databaseOperationExceptionHandler(DatabaseOperationException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessageDto);
    }

}
