package com.soon.petpi.model.dto.HealthStatus;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HealthStatusResponse {

    private Long statusIdx;

    private LocalDate healthDate;

    private int petWeight;

    private String petPoo;

    private String petPee;
}
