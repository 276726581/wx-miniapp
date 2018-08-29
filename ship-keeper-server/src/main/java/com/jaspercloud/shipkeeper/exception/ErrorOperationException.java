package com.jaspercloud.shipkeeper.exception;

public class ErrorOperationException extends RuntimeException {

    public ErrorOperationException() {
    }

    public ErrorOperationException(String message) {
        super(message);
    }

    public ErrorOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorOperationException(Throwable cause) {
        super(cause);
    }
}
