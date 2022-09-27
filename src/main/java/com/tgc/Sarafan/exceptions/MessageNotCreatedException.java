package com.tgc.Sarafan.exceptions;

public class MessageNotCreatedException extends RuntimeException {

    public MessageNotCreatedException() {
        super();
    }

    public MessageNotCreatedException(String msg) {
        super(msg);
    }
}
