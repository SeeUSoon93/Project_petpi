package com.soon.petpi.service;

import com.soon.petpi.exception.type.NoPetError;
import com.soon.petpi.model.dto.pet.PetCalenderResponse;
import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final FileStoreService fileStoreService;

    public Pet save(User user, PetRequest petRequest) throws IOException {

        Pet pet = petRequestToPet(petRequest);
        pet.setUser(user);

        petRepository.save(pet);

        return pet;
    }

    public List<PetResponse> findAll(User user) {

        Optional<List<Pet>> petsOptional = petRepository.findByUser(user);
        return petsOptional.map(pets ->
                pets.stream().map(this::petToPetResponse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public Pet findOne(Long petIdx) {
        return petRepository.findById(petIdx).orElse(null);
    }

    public Pet update(Long petIdx, PetRequest petRequest) throws IOException {

        Pet savedPet = findOne(petIdx);

        if (savedPet == null) {
            throw new NoPetError();
        }

        if (savedPet.getPetImage() !=null && petRequest.getPetImage().equals(savedPet.getPetImage())) {
            fileStoreService.delete(savedPet.getPetImage());
        }

        // Pet 객체 업데이트
        savedPet.setPetName(petRequest.getPetName());
        savedPet.setPetImage(fileStoreService.uploadFile(petRequest.getPetImage()).getStoreName());
        savedPet.setPetGender(petRequest.getPetGender());
        savedPet.setPetSpecies(petRequest.getPetSpecies());
        savedPet.setPetBirthdate(petRequest.getPetBirthdate());

        return petRepository.save(savedPet);
    }

    public Boolean delete(Long petIdx) {

        Pet deletePet = findOne(petIdx);

        if (deletePet==null) {
            throw new NoPetError();
        }

        if (deletePet.getPetImage() != null) {
            fileStoreService.delete(deletePet.getPetImage());
        }

        petRepository.delete(deletePet);

        return true;
    }

    public PetCalenderResponse readCalender(Long petIdx) {
        Pet savedPet = petRepository.findByIdCalender(petIdx).orElse(null);
        if (savedPet == null) {
            throw new NoPetError();
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
                .petImage(pet.getPetImage())
                .build();
    }

    public Pet petRequestToPet(PetRequest petRequest) throws IOException {
        return Pet.builder()
                .petName(petRequest.getPetName())
                .petSpecies(petRequest.getPetSpecies())
                .petBirthdate(petRequest.getPetBirthdate())
                .petImage(fileStoreService.uploadFile(petRequest.getPetImage())
                        .getStoreName())
                .petGender(petRequest.getPetGender())
                .build();
    }

    public PetCalenderResponse petToPetCalenderResponse(Pet pet) {
        return PetCalenderResponse.builder()
                .petIdx(pet.getPetIdx())
                .petName(pet.getPetName())

                .calenderDiseaseStatuses(pet.getDiseaseStatuses().stream().map(
                        disease -> PetCalenderResponse.CalenderDiseaseStatuses.builder()
                                .diseaseIdx(disease.getDiseaseIdx())
                                .date(disease.getDiseaseDate())
                                .diseaseName(disease.getDiseaseName())
                                .diseaseLabel(disease.getDiseaseLabel())
                                .build()
                ).collect(Collectors.toList()))

                .calenderHealthStatuses(pet.getHealthStatuses().stream().map(
                        health -> PetCalenderResponse.CalenderHealthStatuses.builder()
                                .statusIdx(health.getStatusIdx())
                                .date(health.getHealthDate())
                                .petWeight(health.getPetWeight())
                                .petPoo(health.getPetPoo())
                                .petPee(health.getPetPee())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
