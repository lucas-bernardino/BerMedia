package com.movie.server.exception;

public class LoginException extends RuntimeException{

    public LoginException() {
        super("Incorrect username or password");
    }

    public LoginException(String message) {
        super(message);
    }

}
