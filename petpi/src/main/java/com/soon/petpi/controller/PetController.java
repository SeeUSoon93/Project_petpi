package com.soon.petpi.controller;

import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.exception.type.FieldErrorException;
import com.soon.petpi.model.dto.pet.PetCalenderResponse;
import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/pets")
public class PetController {

    private final PetService petService;

    @GetMapping()
    public List<PetResponse> findAllPet(@Login User user) {
        return petService.findAll(user);
    }

    @PostMapping()
    public PetResponse savePet(@Login User user,
                       @Valid @RequestBody PetRequest petRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        return petService.petToPetResponse(petService.save(user, petRequest));
    }

    @GetMapping("/{petIdx}")
    public PetResponse changePet(@PathVariable(name = "petIdx") Long petIdx) {
        return petService.petToPetResponse(petService.findOne(petIdx));
    }

    @PatchMapping("/{petIdx}")
    public PetResponse updatePet(@PathVariable(name = "petIdx") Long petIdx,
                         @Valid @RequestBody PetRequest petRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        return petService.petToPetResponse(petService.update(petIdx, petRequest));
    }

    @DeleteMapping("/{petIdx}")
    public Boolean deletePet(@PathVariable(name = "petIdx") Long petIdx) {
        return petService.delete(petIdx);
    }

    @GetMapping("/calenders/{petIdx}")
    public PetCalenderResponse readCalender(@PathVariable(name = "petIdx") Long petIdx) {
        return petService.readCalender(petIdx);
    }
}
