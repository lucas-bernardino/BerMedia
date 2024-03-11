package com.movie.server.exception;

public class AuthFieldException extends RuntimeException{
    public AuthFieldException(String message) {
        super(message);
    }
}
