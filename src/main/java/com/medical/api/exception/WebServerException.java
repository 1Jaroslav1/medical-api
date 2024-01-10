package com.medical.api.exception;

public class WebServerException extends ApplicationException {
    public WebServerException() {
        super("External server error");
    }
}
