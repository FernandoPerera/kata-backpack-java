package com.kata.backpack.common;

public abstract class Error {

    private String message;

    public Error(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
