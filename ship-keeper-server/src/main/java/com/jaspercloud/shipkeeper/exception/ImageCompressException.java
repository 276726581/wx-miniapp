package com.jaspercloud.shipkeeper.exception;

public class ImageCompressException extends RuntimeException {

    public ImageCompressException() {
    }

    public ImageCompressException(String message) {
        super(message);
    }

    public ImageCompressException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageCompressException(Throwable cause) {
        super(cause);
    }
}
