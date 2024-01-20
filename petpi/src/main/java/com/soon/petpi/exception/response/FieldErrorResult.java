package com.soon.petpi.exception.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Getter @Setter @Builder
@AllArgsConstructor
public class FieldErrorResult {
    private int code;
    private String status;
    private List<FieldErrors> fieldErrors;

    @Getter @Setter @Builder
    @AllArgsConstructor
    public static class FieldErrors {
        private Object inputValue;
        private String fieldName;
        private String message;
    }

    public FieldErrorResult(int code, String status, List<Object> inputValues, List<String> fieldNames, List<String> messages) {
        this.code = code;
        this.status = status;
        this.fieldErrors = IntStream.range(0, fieldNames.size())
                .mapToObj(idx -> FieldErrors.builder()
                        .inputValue(inputValues.get(idx))
                        .fieldName(fieldNames.get(idx))
                        .message(messages.get(idx))
                        .build())
                .toList();
    }
}
