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
    private final ChatRepository chatRepository;
    private final ChatContentRepository chatContentRepository;

    @PostConstruct
    public void postAddUser() {
        User user = User.builder()
                        .userEmail("test")
                        .userPw("test!")
                        .userNick("tester")
                        .userRole(UserRole.ROLE_USER)
                        .build();

        User user2 = User.builder()
                .userEmail("test2")
                .userPw("test!")
                .userNick("tester")
                .userRole(UserRole.ROLE_USER)
                .build();

        User newUser = userRepository.save(user);
        User newUser2 = userRepository.save(user2);

        Pet pet1 = Pet.builder()
                .user(newUser)
                .petName("달봉이")
                .petBirthdate(LocalDate.of(2000, 4, 19))
                .petSpecies(PetSpecies.DOG)
                .petGender(PetGender.MALE)
                .build();

        Pet pet2 = Pet.builder()
                .user(newUser)
                .petName("해봉이")
                .petBirthdate(LocalDate.of(1998, 1, 16))
                .petSpecies(PetSpecies.CAT)
                .petGender(PetGender.FEMALE)
                .build();

        Pet pet3 = Pet.builder()
                .user(newUser2)
                .petName("별봉이")
                .petBirthdate(LocalDate.of(2024, 1, 1))
                .petSpecies(PetSpecies.CAT)
                .petGender(PetGender.FEMALE)
                .build();

        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);

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

        Optional<Pet> petOptional = petRepository.findByIdAndUserIdx(pet1.getPetIdx(), newUser.getUserIdx());

        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();
            log.info("petName = {}", pet.getPetName());
        }

        Chat chat = Chat.builder()
                .chatDate(LocalDate.now())
                .pet(pet1)
                .build();

        chatRepository.save(chat);

        ChatContent chatContent = ChatContent.builder()
                .chat(chat)
                .question("test question")
                .answer("test answer")
                .build();

        chatContentRepository.save(chatContent);
    }
}
