package com.jaspercloud.shipkeeper.exception;

public class SaveFileException extends RuntimeException {

    public SaveFileException() {
    }

    public SaveFileException(String message) {
        super(message);
    }

    public SaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveFileException(Throwable cause) {
        super(cause);
    }
}
