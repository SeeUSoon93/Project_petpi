package com.soon.petpi.model.dto.pet.request;

import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface PetRequest {

    PetSpecies getPetSpecies();
    PetGender getPetGender();
    LocalDate getPetBirthdate();
    String getPetName();
    MultipartFile getPetImage();

}
