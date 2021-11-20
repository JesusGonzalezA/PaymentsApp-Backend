package com.paymentsapp.paymentsapp.exception;

public class UserAlreadyExistsException extends AlreadyExistsException {
    public UserAlreadyExistsException(String username) {
        super("The user " + username + " already exists.");
    }
}
