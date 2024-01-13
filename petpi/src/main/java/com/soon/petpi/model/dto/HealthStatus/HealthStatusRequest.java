package com.soon.petpi.model.dto.HealthStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatusRequest {

    private Integer petWeight;

    private String petPoo;

    private String petPee;

    private LocalDate healthDate;
}
