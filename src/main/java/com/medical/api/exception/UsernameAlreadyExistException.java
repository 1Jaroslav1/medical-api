package com.medical.api.exception;

public class UsernameAlreadyExistException extends ApplicationException{
    public UsernameAlreadyExistException() {
        super("Username already exist. Choose another one");
    }
}

