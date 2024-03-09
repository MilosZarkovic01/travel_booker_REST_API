package com.somika.travelbooker.exception;

public class PasswordResetTokenNotFoundException extends RuntimeException {

    public PasswordResetTokenNotFoundException(String message) {
        super(message);
    }
}
