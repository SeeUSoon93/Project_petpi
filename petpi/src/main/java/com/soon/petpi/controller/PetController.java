package com.soon.petpi.controller;

import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.service.PetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/pet")
public class PetController {

    private final PetService petService;

    @GetMapping()
    public List<Pet> findAllPet(@Login User user) {
        return petService.findAll(user);
    }

    @PostMapping()
    public Pet savePet(@Login User user,
                       @Valid @RequestBody PetRequest petRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return null;
        }
        return petService.save(user, petRequest);
    }

    @GetMapping("/{petIdx}")
    public Pet changePet(@PathVariable(name = "petIdx") Long petIdx) {
        return petService.findOne(petIdx);
    }

    @PatchMapping("/{petIdx}")
    public Pet updatePet(@PathVariable(name = "petIdx") Long petIdx,
                         @Valid @RequestBody PetRequest petRequest) {
        return petService.update(petIdx, petRequest);
    }

    @DeleteMapping("/{petIdx}")
    public Boolean deletePet(@PathVariable(name = "petIdx") Long petIdx) {
        return petService.delete(petIdx);
    }

    @GetMapping("/calender/{petIdx}")
    public Pet readCalender(@PathVariable(name = "petIdx") Long petIdx) {
        return petService.readCalender(petIdx);
    }
}
