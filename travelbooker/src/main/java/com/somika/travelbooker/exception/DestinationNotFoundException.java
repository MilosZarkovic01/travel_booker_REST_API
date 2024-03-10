package com.somika.travelbooker.exception;

public class DestinationNotFoundException extends RuntimeException {

    public DestinationNotFoundException(String message) {
        super(message);
    }
}
