package com.soon.petpi.controller;

import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.entity.*;
import com.soon.petpi.model.label.DiseaseLabel;
import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import com.soon.petpi.model.label.UserRole;
import com.soon.petpi.repository.*;
import com.soon.petpi.service.PetService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final HealthStatusRepository healthStatusRepository;
    private final DiseaseStatusRepository diseaseStatusRepository;
    private final PetService petService;
    private final ChatRepository chatRepository;

    @PostConstruct
    public void postAddUser() {
        User user = User.builder()
                        .userEmail("test")
                        .userPw("test!")
                        .userNick("tester")
                        .userRole(UserRole.USER)
                        .build();

        User newUser = userRepository.save(user);

        Pet pet1 = Pet.builder()
                .user(user)
                .petName("달봉이")
                .petBirthdate(LocalDate.of(2000, 4, 19))
                .petSpecies(PetSpecies.DOG)
                .petGender(PetGender.MALE)
                .build();

        Pet pet2 = Pet.builder()
                .user(user)
                .petName("해봉이")
                .petBirthdate(LocalDate.of(1998, 1, 16))
                .petSpecies(PetSpecies.CAT)
                .petGender(PetGender.FEMALE)
                .build();

        petRepository.save(pet1);
        petRepository.save(pet2);

        HealthStatus healthStatus = HealthStatus.builder()
                .pet(pet1)
                .petWeight(2)
                .petPee("양호")
                .petPoo("주의")
                .build();

        healthStatusRepository.save(healthStatus);

        DiseaseStatus diseaseStatus = DiseaseStatus.builder()
                .pet(pet1)
                .diseaseName("피부병")
                .diseaseLabel(DiseaseLabel.AI)
                .build();

        diseaseStatusRepository.save(diseaseStatus);

        Optional<Pet> petOptional = petRepository.findByIdEntityGraph(pet1.getPetIdx());

        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();
            log.info("petName = {}", pet.getPetName());
        }

        List<PetResponse> pets = petService.findAll(newUser);

        log.info("petName = [{}][{}]", pets.get(0).getPetName(), pets.get(1).getPetName());

        Chat chat = Chat.builder()
                .chatDate(LocalDate.now())
                .chatContent("test")
                .pet(pet1)
                .build();

        chatRepository.save(chat);

    }
}
