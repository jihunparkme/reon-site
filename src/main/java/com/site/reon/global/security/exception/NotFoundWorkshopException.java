package com.site.reon.global.security.exception;

public class NotFoundWorkshopException extends RuntimeException {
    public NotFoundWorkshopException() {
        super("Not Found Workshop");
    }

    public NotFoundWorkshopException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundWorkshopException(String message) {
        super(message);
    }

    public NotFoundWorkshopException(Throwable cause) {
        super(cause);
    }
}
