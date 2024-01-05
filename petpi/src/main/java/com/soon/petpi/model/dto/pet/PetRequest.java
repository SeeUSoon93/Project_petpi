package com.soon.petpi.model.dto.pet;

import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {

    private static PetGender[] genderList = {PetGender.MALE, PetGender.FEMALE};
    private static PetSpecies[] speciesList = {PetSpecies.DOG, PetSpecies.CAT};

    @NotNull
    @Range(min = 0, max = 1)
    private int petSpecies;

    @NotNull
    @Range(min = 0, max = 1)
    private int petGender;

    @NotNull
    private LocalDate petBirthdate;

    @NotBlank
    private String petName;

    private MultipartFile petImage;

    public PetSpecies getPetSpecies() {
        return speciesList[petSpecies];
    }

    public PetGender getPetGender() {
        return genderList[petGender];
    }

}
