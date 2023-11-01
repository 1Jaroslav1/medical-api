package com.medical.api.exception;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException() {
        super("Choose correct chat");
    }
}
