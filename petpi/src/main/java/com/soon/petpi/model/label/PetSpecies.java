package com.soon.petpi.model.label;

import com.soon.petpi.service.PetService;
import lombok.Getter;

@Getter
public enum PetSpecies {
    DOG("개"), CAT("고양이");

    private final String speciesName;

    PetSpecies(String speciesName) {
        this.speciesName = speciesName;
    }
}
