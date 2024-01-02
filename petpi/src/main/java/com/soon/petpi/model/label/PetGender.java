package com.soon.petpi.model.label;

import lombok.Getter;

@Getter
public enum PetGender {
    MALE("수컷"), FEMALE("암컷");

    private final String petGender;

    PetGender(String petGender) {
        this.petGender = petGender;
    }
}
