package com.tgc.Sarafan.exceptions;

public class CommentNotCreatedException extends RuntimeException {

    public CommentNotCreatedException() {

    }

    public CommentNotCreatedException(String msg) {
        super(msg);
    }
}
