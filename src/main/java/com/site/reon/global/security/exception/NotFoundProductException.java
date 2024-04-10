package com.site.reon.global.security.exception;

public class NotFoundProductException extends RuntimeException {
    public NotFoundProductException() {
        super("Not Found Product");
    }

    public NotFoundProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundProductException(String message) {
        super(message);
    }

    public NotFoundProductException(Throwable cause) {
        super(cause);
    }
}
