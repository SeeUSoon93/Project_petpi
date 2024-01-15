package com.soon.petpi.model.dto.DiseaseStatus.request;

import com.soon.petpi.model.label.DiseaseLabel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseStatusSaveForm implements DiseaseStatusRequest{

    @NotBlank
    private String diseaseName;

    @NotNull
    private DiseaseLabel diseaseLabel;

    @NotNull
    @Past
    private LocalDate diseaseDate;
}
