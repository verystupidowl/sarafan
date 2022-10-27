package com.tgc.Sarafan.exceptions;

public class UserWithIdNotFoundException extends RuntimeException {

    public UserWithIdNotFoundException(String msg) {
        super(msg);
    }

    public UserWithIdNotFoundException() {
        super();
    }
}
