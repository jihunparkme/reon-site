package com.site.reon.global.security.exception;

public class NotFoundMemberException extends RuntimeException {
    public NotFoundMemberException() {
        super("Not Found Member");
    }

    public NotFoundMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMemberException(String message) {
        super(message);
    }

    public NotFoundMemberException(Throwable cause) {
        super(cause);
    }
}
