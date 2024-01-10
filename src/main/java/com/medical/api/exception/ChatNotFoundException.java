package com.medical.api.exception;

public class ChatNotFoundException extends ApplicationException{
    public ChatNotFoundException() {
        super("Choose correct chat");
    }
}
