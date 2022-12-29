package com.trollmarket.exceptionhandler;

public class ObjectNotFound extends RuntimeException {

    public ObjectNotFound() {
        super();
    }

    public ObjectNotFound(String message) {
        super(message);
    }

}
