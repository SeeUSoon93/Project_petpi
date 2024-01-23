package com.soon.petpi.model.dto.HealthStatus.request;

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
public class HealthStatusUpdateForm implements HealthStatusRequest{

    private int petWeight;

    private String petPoo;

    private String petPee;

    @Past
    private LocalDate healthDate;
}
