package com.soon.petpi.model.dto.pet;

import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {

    private static PetGender[] genderList = {PetGender.MALE, PetGender.FEMALE};
    private static PetSpecies[] speciesList = {PetSpecies.DOG, PetSpecies.CAT};

    @Range(min = 0, max = 1)
    private int petSpecies;

    @Range(min = 0, max = 1)
    private int petGender;

    @Getter
    @NotBlank
    private String petName;

    public PetSpecies getPetSpecies() {
        return speciesList[petSpecies];
    }

    public PetGender getPetGender() {
        return genderList[petGender];
    }

}
