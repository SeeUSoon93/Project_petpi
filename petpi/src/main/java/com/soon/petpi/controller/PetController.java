package com.soon.petpi.controller;

import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.exception.type.FieldErrorException;
import com.soon.petpi.model.dto.pet.PetCalenderResponse;
import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/pets")
public class PetController {

    private final PetService petService;

    @GetMapping()
    public List<PetResponse> findAllPet(@Login Long userIdx) {
        return petService.findAll(userIdx);
    }

    @PostMapping()
    public PetResponse savePet(@Login Long userIdx,
                       @Valid @ModelAttribute PetRequest petRequest, BindingResult bindingResult) throws IOException {

        // fieldError가 발생했는지 검증하여 에러가 존재하면 FieldException
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        return petService.petToPetResponse(petService.save(userIdx, petRequest));
    }

    @GetMapping("/{petIdx}")
    public PetResponse changePet(@Login Long userIdx, @PathVariable(name = "petIdx") Long petIdx) {
        return petService.petToPetResponse(petService.findOne(petIdx, userIdx));
    }

    @PatchMapping("/{petIdx}")
    public PetResponse updatePet(@Login Long userIdx, @PathVariable(name = "petIdx") Long petIdx,
                         @Valid @ModelAttribute PetRequest petRequest, BindingResult bindingResult) throws IOException {
        // fieldError가 발생했는지 검증하여 에러가 존재하면 FieldException
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        return petService.petToPetResponse(petService.update(petIdx, userIdx, petRequest));
    }

    @DeleteMapping("/{petIdx}")
    public Boolean deletePet(@Login Long userIdx, @PathVariable(name = "petIdx") Long petIdx) {
        return petService.delete(petIdx, userIdx);
    }

    @GetMapping("/{petIdx}/calenders")
    public PetCalenderResponse readCalender(@Login Long userIdx, @PathVariable(name = "petIdx") Long petIdx) {
        return petService.readCalender(petIdx, userIdx);
    }
}
