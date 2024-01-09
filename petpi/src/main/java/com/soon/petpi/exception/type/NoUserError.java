package com.soon.petpi.exception.type;

public class NoUserError extends RuntimeException {
    public NoUserError() {
        super();
    }

    public NoUserError(String message) {
        super(message);
    }

    public NoUserError(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUserError(Throwable cause) {
        super(cause);
    }

    protected NoUserError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
