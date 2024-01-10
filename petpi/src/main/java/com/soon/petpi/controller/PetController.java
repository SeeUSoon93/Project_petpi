package com.soon.petpi.controller;

import com.soon.petpi.exception.type.FieldErrorException;
import com.soon.petpi.model.dto.DeleteResult;
import com.soon.petpi.model.dto.pet.PetCalenderResponse;
import com.soon.petpi.model.dto.pet.PetRequest;
import com.soon.petpi.model.dto.pet.PetResponse;
import com.soon.petpi.model.dto.pet.PetSaveForm;
import com.soon.petpi.service.PetService;
import com.soon.petpi.service.assembler.PetResponseAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userIdx}/pets")
public class PetController {

    private final PetService petService;
    private final PetResponseAssembler assembler;

    @GetMapping()
    public CollectionModel<EntityModel<PetResponse>> findAllPet(@PathVariable(name = "userIdx") Long userIdx) {
        List<EntityModel<PetResponse>> petResponses = petService.findAll(userIdx).stream()
                .peek(petResponse -> petResponse.setUserIdx(userIdx))
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(petResponses,
                linkTo(methodOn(PetController.class).findAllPet(userIdx)).withSelfRel()
        );
    }

    @PostMapping()
    public EntityModel<PetResponse> savePet(@PathVariable(name = "userIdx") Long userIdx,
                                            @Valid @ModelAttribute PetSaveForm petSaveForm,
                                            BindingResult bindingResult) throws IOException {

        if (petSaveForm.getPetImage() == null) {
            bindingResult.rejectValue("petImage", "error.petImage", "요청에 petImage 파라미터가 누락되었습니다");
        }

        // fieldError가 발생했는지 검증하여 에러가 존재하면 FieldException
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        PetResponse petResponse = petService.petToPetResponse(petService.save(userIdx, petSaveForm));
        petResponse.setUserIdx(userIdx);
        petResponse.setBindingResult(bindingResult);

        return assembler.toModel(petResponse);
    }

    @GetMapping("/{petIdx}")
    public EntityModel<PetResponse> findOnePet(@PathVariable(name = "userIdx") Long userIdx,
                                  @PathVariable(name = "petIdx") Long petIdx) {

        PetResponse petResponse = petService.petToPetResponse(petService.findOne(petIdx, userIdx));
        petResponse.setUserIdx(userIdx);

        return assembler.toModel(petResponse);
    }

    @PatchMapping("/{petIdx}")
    public EntityModel<PetResponse> updatePet(@PathVariable(name = "userIdx") Long userIdx,
                                 @PathVariable(name = "petIdx") Long petIdx,
                                 @Valid @ModelAttribute PetRequest petRequest,
                                 BindingResult bindingResult) throws IOException {

        // fieldError가 발생했는지 검증하여 에러가 존재하면 FieldException
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        PetResponse petResponse = petService.petToPetResponse(petService.update(petIdx, userIdx, petRequest));
        petResponse.setUserIdx(userIdx);

        return assembler.toModel(petResponse);
    }

    @DeleteMapping("/{petIdx}")
    public DeleteResult deletePet(@PathVariable(name = "userIdx") Long userIdx,
                                  @PathVariable(name = "petIdx") Long petIdx) {

        petService.delete(petIdx, userIdx);

        return new DeleteResult(200, "반려동물의 정보가 삭제되었습니다.");
    }

    @GetMapping("/{petIdx}/calenders")
    public EntityModel<PetCalenderResponse> readCalender(@PathVariable(name = "userIdx") Long userIdx,
                                            @PathVariable(name = "petIdx") Long petIdx) {

        PetCalenderResponse response = petService.readCalender(petIdx, userIdx);
        return EntityModel.of(response,
                linkTo(methodOn(PetController.class).readCalender(userIdx, petIdx)).withSelfRel()
        );
    }
}
