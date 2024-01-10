package com.soon.petpi.model.dto.pet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

@Data
@Builder
public class PetResponse {
    private Long petIdx;
    private String petSpecies;
    private LocalDate petBirthdate;
    private String petGender;
    private String petName;
    private String petImage;

    @JsonIgnore
    private Long userIdx;
    @JsonIgnore
    private PetRequest petRequest;
    @JsonIgnore
    private BindingResult bindingResult;
}