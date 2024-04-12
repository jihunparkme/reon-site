package com.site.reon.global.security.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
        super("This email is already registered.");
    }

    public DuplicateMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateMemberException(String message) {
        super(message);
    }

    public DuplicateMemberException(Throwable cause) {
        super(cause);
    }
}