package com.soon.petpi.model.label;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("회원"), DOCTOR("수의사");

    private UserRole(String role) {
        this.role = role;
    }
    private final String role;
}
