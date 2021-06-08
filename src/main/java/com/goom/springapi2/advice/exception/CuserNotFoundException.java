package com.goom.springapi2.advice.exception;

public class CuserNotFoundException extends RuntimeException{
    public CuserNotFoundException() {
        super();
    }

    public CuserNotFoundException(String message) {
        super(message);
    }

    public CuserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
