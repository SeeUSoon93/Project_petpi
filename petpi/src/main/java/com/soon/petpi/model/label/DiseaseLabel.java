package com.soon.petpi.model.label;

import lombok.Getter;

@Getter
public enum DiseaseLabel {
    AI("AI"), DOCTOR("수의사");

    private final String labelName;

    DiseaseLabel(String labelName) {
        this.labelName = labelName;
    }
}
