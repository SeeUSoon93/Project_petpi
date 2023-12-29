package com.soon.petpi.service;

import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.repository.PetRepository;
import com.soon.petpi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Pet save(User user, PetRequest petRequest) {

        if (user == null) {
            return null;
        }

        Pet pet = Pet.builder()
                .user(user)
                .petName(petRequest.getPetName())
                .petSpecies(petRequest.getPetSpecies())
                .petGender(petRequest.getPetGender())
                .build();

        petRepository.save(pet);

        return pet;
    }

    public List<Pet> findAll(User user) {

        if (user == null) {
            return null;
        }

        Optional<List<Pet>> petsOptional = petRepository.findByUser(user);
        return petsOptional.orElse(null);
    }

    public Pet findOne(Long petIdx) {
        return petRepository.findById(petIdx).orElse(null);
    }

    public Pet update(Long petIdx, PetRequest petRequest) {

        Pet savedPet = findOne(petIdx);

        if (savedPet == null) {
            return null;
        }

        Pet updatePet = Pet.builder()
                .petIdx(savedPet.getPetIdx())
                .user(savedPet.getUser())
                .petSpecies(petRequest.getPetSpecies())
                .petGender(petRequest.getPetGender())
                .petName(petRequest.getPetName())
                .build();

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

    public Pet readCalender(Long petIdx) {
        return petRepository.findByIdCalender(petIdx).orElse(null);
    }

}
