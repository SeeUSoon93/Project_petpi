package com.soon.petpi.exception.type;

import lombok.Getter;
import org.springframework.validation.BindingResult;
@Getter
public class FieldErrorException extends RuntimeException {

    private BindingResult bindingResult;

    public FieldErrorException() {
        super();
    }

    public FieldErrorException(BindingResult bindingResult) {
        super();
        this.bindingResult = bindingResult;
    }

    public FieldErrorException(String message) {
        super(message);
    }

    public FieldErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldErrorException(Throwable cause) {
        super(cause);
    }

    protected FieldErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
