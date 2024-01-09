package com.soon.petpi.exception;

import com.soon.petpi.exception.response.ErrorResult;
import com.soon.petpi.exception.response.FieldErrorResult;
import com.soon.petpi.exception.type.FieldErrorException;
import com.soon.petpi.exception.type.NoPetError;
import com.soon.petpi.exception.type.NoUserError;
import com.soon.petpi.exception.type.SessionError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ExceptionControllerAdvice {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FieldErrorException.class)
    public FieldErrorResult fieldError(FieldErrorException e) {

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<String> fieldNames = fieldErrors.stream()
                .map(FieldError::getField)
                .toList();

        List<String> messages = fieldErrors.stream()
                .map(fieldError -> getMessage(
                        Arrays.stream(fieldError.getCodes()).findFirst().orElse(null),
                        fieldError.getDefaultMessage()))
                .toList();

        return new FieldErrorResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), fieldNames, messages);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult fieldError(HttpMessageNotReadableException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "파라미터 값의 형식이 잘못되었습니다");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoPetError.class)
    public ErrorResult noPetError(NoPetError e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "보유하지 않은 반려동물입니다");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SessionError.class)
    public ErrorResult sessionError(SessionError e) {
        return new ErrorResult(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), "로그인되지 않은 사용자입니다");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoUserError.class)
    public ErrorResult noUserError(NoUserError e) {
        return new ErrorResult(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), "존재하지 않는 사용자입니다");
    }

    public String getMessage(String code, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(code, new Object[]{}, locale);
        } catch (NoSuchMessageException e) {
            return defaultMessage;
        }
    }
}
