package com.soon.petpi.model.label;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_USER("회원"), ROLE_DOCTOR("수의사");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }
}
