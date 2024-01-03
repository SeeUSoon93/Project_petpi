package com.soon.petpi.exception.type;

public class SessionError extends RuntimeException{
    public SessionError() {
        super();
    }

    public SessionError(String message) {
        super(message);
    }

    public SessionError(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionError(Throwable cause) {
        super(cause);
    }

    protected SessionError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
