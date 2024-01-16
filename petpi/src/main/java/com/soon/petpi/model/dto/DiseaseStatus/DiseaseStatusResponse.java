package com.soon.petpi.model.dto.DiseaseStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Long petIdx;
}
