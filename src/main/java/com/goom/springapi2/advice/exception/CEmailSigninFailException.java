package com.goom.springapi2.advice.exception;

public class CEmailSigninFailException extends RuntimeException{
    public CEmailSigninFailException() {
    }

    public CEmailSigninFailException(String message) {
        super(message);
    }

    public CEmailSigninFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
