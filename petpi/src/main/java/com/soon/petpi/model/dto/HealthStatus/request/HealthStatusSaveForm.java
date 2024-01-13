package com.soon.petpi.model.dto.HealthStatus.request;

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
public class HealthStatusSaveForm implements HealthStatusRequest{

    @NotNull
    private int petWeight;

    @NotBlank
    private String petPoo;

    @NotBlank
    private String petPee;

    @NotNull
    @Past
    private LocalDate healthDate;
}
