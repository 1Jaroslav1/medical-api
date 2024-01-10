package com.medical.api.exception;

public class EmailAlreadyExistException extends ApplicationException{
    public EmailAlreadyExistException() {
        super("Email already exist. Choose another one");
    }
}

