package com.soon.petpi.model.dto.pet;

import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PetResponse {
    private Long petIdx;
    private String petSpecies;
    private LocalDate petBirthdate;
    private String petGender;
    private String petName;
}
