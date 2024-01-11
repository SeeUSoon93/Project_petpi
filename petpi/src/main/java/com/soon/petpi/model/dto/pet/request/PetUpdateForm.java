package com.soon.petpi.model.dto.pet.request;

import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@ToString
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateForm implements PetRequest {

    private static PetGender[] genderList = {PetGender.MALE, PetGender.FEMALE};
    private static PetSpecies[] speciesList = {PetSpecies.DOG, PetSpecies.CAT};

    @Range(min = 0, max = 1)
    private Integer petSpecies;

    @Range(min = 0, max = 1)
    private Integer petGender;

    @Past
    private LocalDate petBirthdate;

    private String petName;

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
