package com.movie.server.exception;

public class DatabaseOperationException extends RuntimeException{

    public DatabaseOperationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
