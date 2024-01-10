package com.soon.petpi.service;

import com.soon.petpi.exception.type.NoPetError;
import com.soon.petpi.model.dto.pet.PetCalenderResponse;
import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.dto.pet.PetSaveForm;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.repository.PetRepository;
import com.soon.petpi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final UserRepository userRepository;

    public Pet save(Long userIdx, PetSaveForm petSaveForm) throws IOException {

        User petOwner = userRepository.findById(userIdx).orElse(null);

        Pet pet = petSaveFormToPet(petSaveForm);
        pet.setUser(petOwner);

        petRepository.save(pet);

        return pet;
    }

    public List<PetResponse> findAll(Long userIdx) {

        Optional<List<Pet>> petsOptional = petRepository.findByUserIdx(userIdx);
        return petsOptional.map(pets ->
                pets.stream().map(this::petToPetResponse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public Pet findOne(Long petIdx, Long userIdx) {
        Pet pet = petRepository.findByIdAndUserIdx(petIdx, userIdx).orElse(null);

        if (pet == null) {
            throw new NoPetError();
        }
        return pet;
    }

    public Pet update(Long petIdx, Long userIdx, PetRequest petRequest) throws IOException {

        Pet savedPet = findOne(petIdx, userIdx);

        log.info(petRequest.toString());

        // Pet 객체 업데이트
        if (petRequest.getPetName() != null) {
            savedPet.setPetName(petRequest.getPetName());
        }
        if (petRequest.getPetImage() != null) {
            fileStoreService.delete(savedPet.getPetImage());
            savedPet.setPetImage(fileStoreService.uploadFile(petRequest.getPetImage()).getStoreName());
        }
        if (petRequest.getPetGender() != null) {
            savedPet.setPetGender(petRequest.getPetGender());
        }
        if (petRequest.getPetSpecies() != null) {
            savedPet.setPetSpecies(petRequest.getPetSpecies());
        }
        if (petRequest.getPetBirthdate() != null) {
            savedPet.setPetBirthdate(petRequest.getPetBirthdate());
        }

        return petRepository.save(savedPet);
    }

    public Boolean delete(Long petIdx, Long userIdx) {

        Pet deletePet = findOne(petIdx, userIdx);

        if (deletePet.getPetImage() != null) {
            fileStoreService.delete(deletePet.getPetImage());
        }

        petRepository.delete(deletePet);

        return true;
    }

    public PetCalenderResponse readCalender(Long petIdx, Long userIdx) {
        Pet savedPet = petRepository.findByIdCalenderAndUserIdx(petIdx, userIdx).orElse(null);

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

    public Pet petSaveFormToPet(PetSaveForm petSaveForm) throws IOException {
        return Pet.builder()
                .petName(petSaveForm.getPetName())
                .petSpecies(petSaveForm.getPetSpecies())
                .petBirthdate(petSaveForm.getPetBirthdate())
                .petImage(fileStoreService.uploadFile(petSaveForm.getPetImage())
                        .getStoreName())
                .petGender(petSaveForm.getPetGender())
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
