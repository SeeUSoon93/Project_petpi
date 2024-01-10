package com.soon.petpi.exception.type;

public class NoPetError extends RuntimeException {
    public NoPetError() {
        super();
    }

    public NoPetError(String message) {
        super(message);
    }

    public NoPetError(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPetError(Throwable cause) {
        super(cause);
    }

    protected NoPetError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
