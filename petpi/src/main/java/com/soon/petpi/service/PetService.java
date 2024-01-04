package com.soon.petpi.service;

import com.soon.petpi.model.dto.pet.PetCalenderResponse;
import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public Pet save(User user, PetRequest petRequest) {

        if (user == null) {
            return null;
        }

        Pet pet = petRequestToPet(petRequest);
        pet.setUser(user);

        petRepository.save(pet);

        return pet;
    }

    public List<PetResponse> findAll(User user) {

        if (user == null) {
            return null;
        }

        Optional<List<Pet>> petsOptional = petRepository.findByUser(user);
        return petsOptional.map(pets ->
                pets.stream().map(this::petToPetResponse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public Pet findOne(Long petIdx) {
        return petRepository.findById(petIdx).orElse(null);
    }

    public Pet update(Long petIdx, PetRequest petRequest) {

        Pet savedPet = findOne(petIdx);

        if (savedPet == null) {
            return null;
        }

        Pet updatePet = petRequestToPet(petRequest);
        updatePet.setPetIdx(savedPet.getPetIdx());
        updatePet.setUser(savedPet.getUser());

        return petRepository.save(updatePet);
    }

    public Boolean delete(Long petIdx) {

        Pet deletePet = findOne(petIdx);

        if(deletePet==null) {
            return false;
        }

        petRepository.delete(deletePet);

        return true;
    }

    public PetCalenderResponse readCalender(Long petIdx) {
        Pet savedPet = petRepository.findByIdCalender(petIdx).orElse(null);

        if (savedPet == null) {
            return null;
        }

        return petToPetCalenderResponse(savedPet);
    }

    public PetResponse petToPetResponse(Pet pet) {
        return PetResponse.builder()
                .petIdx(pet.getPetIdx())
                .petSpecies(pet.getPetSpecies())
                .petBirthdate(pet.getPetBirthdate())
                .petName(pet.getPetName())
                .petGender(pet.getPetGender())
                .build();
    }

    public Pet petRequestToPet(PetRequest petRequest) {
        return Pet.builder()
                .petName(petRequest.getPetName())
                .petSpecies(petRequest.getPetSpecies())
                .petBirthdate(petRequest.getPetBirthdate())
                .petGender(petRequest.getPetGender())
                .build();
    }

    public PetCalenderResponse petToPetCalenderResponse(Pet pet) {
        return PetCalenderResponse.builder()
                .petName(pet.getPetName())

                .calenderDiseaseStatuses(pet.getDiseaseStatuses().stream().map(
                        disease -> PetCalenderResponse.CalenderDiseaseStatuses.builder()
                                .date(disease.getDiseaseDate())
                                .diseaseName(disease.getDiseaseName())
                                .diseaseLabel(disease.getDiseaseLabel())
                                .build()
                ).collect(Collectors.toList()))

                .calenderHealthStatuses(pet.getHealthStatuses().stream().map(
                        health -> PetCalenderResponse.CalenderHealthStatuses.builder()
                                .date(health.getHealthDate())
                                .petWeight(health.getPetWeight())
                                .petPoo(health.getPetPoo())
                                .petPee(health.getPetPee())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

}
