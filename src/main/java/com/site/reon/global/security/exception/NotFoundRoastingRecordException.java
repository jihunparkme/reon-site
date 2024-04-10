package com.site.reon.global.security.exception;

public class NotFoundRoastingRecordException extends RuntimeException {
    public NotFoundRoastingRecordException() {
        super("Not Found Roasting Record");
    }

    public NotFoundRoastingRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundRoastingRecordException(String message) {
        super(message);
    }

    public NotFoundRoastingRecordException(Throwable cause) {
        super(cause);
    }
}