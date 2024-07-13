package com.site.reon.global.security.exception;

public class DataAccessPermissionException extends RuntimeException {
    public DataAccessPermissionException() {
        super("You do not have permission to access this data.");
    }

    public DataAccessPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessPermissionException(String message) {
        super(message);
    }

    public DataAccessPermissionException(Throwable cause) {
        super(cause);
    }
}
