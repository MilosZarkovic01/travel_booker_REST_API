package com.somika.travelbooker.exception;

public class AccountIsAlreadyActivatedException extends RuntimeException {

    public AccountIsAlreadyActivatedException(String message) {
        super(message);
    }
}
