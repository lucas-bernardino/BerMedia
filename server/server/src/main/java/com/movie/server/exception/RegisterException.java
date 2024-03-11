package com.movie.server.exception;

public class RegisterException extends RuntimeException{

    public RegisterException() {
        super("Username already taken.");
    }

}
