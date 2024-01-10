package com.soon.petpi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteResult {
    private int code;
    private String message;
}
