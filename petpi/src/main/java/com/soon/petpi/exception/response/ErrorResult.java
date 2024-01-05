package com.soon.petpi.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private int code;
    private String status;
    private String message;
}
