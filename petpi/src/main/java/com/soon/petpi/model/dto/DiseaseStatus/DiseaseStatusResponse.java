package com.soon.petpi.model.dto.DiseaseStatus;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DiseaseStatusResponse {

    private Long diseaseIdx;

    private LocalDate diseaseDate;

    private String diseaseName;

    private String diseaseLabel;
}
