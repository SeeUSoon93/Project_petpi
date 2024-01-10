package com.soon.petpi.model.dto.pet;

import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetSaveForm {

    private static PetGender[] genderList = {PetGender.MALE, PetGender.FEMALE};
    private static PetSpecies[] speciesList = {PetSpecies.DOG, PetSpecies.CAT};

    @NotNull
    @Range(min = 0, max = 1)
    private Integer petSpecies;

    @NotNull
    @Range(min = 0, max = 1)
    private Integer petGender;

    @NotNull
    private LocalDate petBirthdate;

    @NotBlank
    private String petName;

    @Nullable
    private MultipartFile petImage;

    public PetSpecies getPetSpecies() {
        if (this.petSpecies != null) {
            return speciesList[petSpecies];
        }
        return null;
    }

    public PetGender getPetGender() {
        if (this.petGender != null) {
            return genderList[petGender];
        }
        return null;
    }

}
