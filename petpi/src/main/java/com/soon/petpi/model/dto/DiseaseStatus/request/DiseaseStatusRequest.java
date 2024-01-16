package com.soon.petpi.model.dto.DiseaseStatus.request;

import com.soon.petpi.model.label.DiseaseLabel;

import java.time.LocalDate;

public interface DiseaseStatusRequest {

    String getDiseaseName();
    DiseaseLabel getDiseaseLabel();
    LocalDate getDiseaseDate();
}
