package com.soon.petpi.model.dto.HealthStatus.request;

import java.time.LocalDate;

public interface HealthStatusRequest {

    int getPetWeight();
    String getPetPoo();
    String getPetPee();
    LocalDate getHealthDate();
}
