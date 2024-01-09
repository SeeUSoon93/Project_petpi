package com.soon.petpi.model.dto.DiseaseStatus;

import com.soon.petpi.model.label.DiseaseLabel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseStatusRequest {

    @NotBlank
    private String diseaseName;

    private DiseaseLabel diseaseLabel;
}
