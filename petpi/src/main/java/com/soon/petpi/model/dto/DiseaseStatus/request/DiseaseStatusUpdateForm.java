package com.soon.petpi.model.dto.DiseaseStatus.request;

import com.soon.petpi.model.label.DiseaseLabel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseStatusUpdateForm implements DiseaseStatusRequest{

    private String diseaseName;

    private DiseaseLabel diseaseLabel;

    @Past
    private LocalDate diseaseDate;
}
