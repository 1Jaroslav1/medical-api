package com.medical.api.exception;

public class WebServerException extends RuntimeException {
    public WebServerException() {super("External server error");}
}
