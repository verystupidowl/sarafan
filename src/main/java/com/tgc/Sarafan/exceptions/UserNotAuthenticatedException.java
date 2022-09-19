package com.tgc.Sarafan.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException(String msg) {
        super(msg);
    }

    public UserNotAuthenticatedException() {
        super();
    }
}
