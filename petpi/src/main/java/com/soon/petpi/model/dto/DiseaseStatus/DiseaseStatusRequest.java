package com.soon.petpi.model.dto.DiseaseStatus;

import com.soon.petpi.model.label.DiseaseLabel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseStatusRequest {

    @NotBlank
    private String diseaseName;

    private DiseaseLabel diseaseLabel;

    private LocalDate diseaseDate;
}
