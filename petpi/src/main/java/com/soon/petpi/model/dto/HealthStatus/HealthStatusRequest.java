package com.soon.petpi.model.dto.HealthStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatusRequest {

    private int petWeight;

    private String petPoo;

    private String petPee;
}
