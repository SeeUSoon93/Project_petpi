package com.soon.petpi.service;

import com.soon.petpi.exception.type.NoPetError;
import com.soon.petpi.model.dto.pet.PetCalenderResponse;
import com.soon.petpi.model.dto.pet.request.PetRequest;
import com.soon.petpi.model.dto.pet.request.PetUpdateForm;
import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.dto.pet.request.PetSaveForm;
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

        Pet pet = petRequsetToPet(petSaveForm);
        pet.setUser(petOwner);

        petRepository.save(pet);

        return pet;
    }

    public List<Pet> findAll(Long userIdx) {
        return petRepository.findByUserIdx(userIdx).orElse(null);
    }

    public Pet findOne(Long petIdx, Long userIdx) {
        Pet pet = petRepository.findByIdAndUserIdx(petIdx, userIdx).orElse(null);

        if (pet == null) {
            throw new NoPetError();
        }
        return pet;
    }

    public Pet update(Long petIdx, Long userIdx, PetUpdateForm petUpdateForm) throws IOException {

        Pet savedPet = findOne(petIdx, userIdx);

        log.info(petUpdateForm.toString());

        // PetUpdateForm 변경 값이 존재하는 지 판단하고 해당 부분만을 수정
        if (petUpdateForm.getPetName() != null) {
            savedPet.setPetName(petUpdateForm.getPetName());
        }
        if (petUpdateForm.getPetImage() != null) {
            fileStoreService.delete(savedPet.getPetImage());
            savedPet.setPetImage(fileStoreService.uploadFile(petUpdateForm.getPetImage()).getStoreName());
        }
        if (petUpdateForm.getPetGender() != null) {
            savedPet.setPetGender(petUpdateForm.getPetGender());
        }
        if (petUpdateForm.getPetSpecies() != null) {
            savedPet.setPetSpecies(petUpdateForm.getPetSpecies());
        }
        if (petUpdateForm.getPetBirthdate() != null) {
            savedPet.setPetBirthdate(petUpdateForm.getPetBirthdate());
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

    public PetResponse petToPetResponse(Pet pet, Long userIdx) {
        return PetResponse.builder()
                .userIdx(userIdx)
                .petIdx(pet.getPetIdx())
                .petSpecies(pet.getPetSpecies())
                .petBirthdate(pet.getPetBirthdate())
                .petName(pet.getPetName())
                .petGender(pet.getPetGender())
                .petImage(pet.getPetImage())
                .build();
    }

    public Pet petRequsetToPet(PetRequest request) throws IOException {
        return Pet.builder()
                .petName(request.getPetName())
                .petSpecies(request.getPetSpecies())
                .petBirthdate(request.getPetBirthdate())
                .petImage(fileStoreService.uploadFile(request.getPetImage())
                        .getStoreName())
                .petGender(request.getPetGender())
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
                ).toList())

                .calenderHealthStatuses(pet.getHealthStatuses().stream().map(
                        health -> PetCalenderResponse.CalenderHealthStatuses.builder()
                                .statusIdx(health.getStatusIdx())
                                .date(health.getHealthDate())
                                .petWeight(health.getPetWeight())
                                .petPoo(health.getPetPoo())
                                .petPee(health.getPetPee())
                                .build()
                ).toList())
                .build();
    }
}
