package com.soon.petpi.controller;

import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.exception.type.FieldErrorException;
import com.soon.petpi.model.dto.DeleteResult;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusRequest;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusResponse;
import com.soon.petpi.model.dto.DiseaseStatus.request.DiseaseStatusSaveForm;
import com.soon.petpi.model.dto.DiseaseStatus.request.DiseaseStatusUpdateForm;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.service.DiseaseStatusService;
import com.soon.petpi.service.assembler.DiseaseStatusResponseAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user/pets/{petIdx}/disease-statuses")
public class DiseaseStatusController {

    private final DiseaseStatusService diseaseStatusService;
    private final DiseaseStatusResponseAssembler assembler;

    @PostMapping()
    public EntityModel<DiseaseStatusResponse> saveDiseaseStatus(@PathVariable(name = "petIdx") Long petIdx,
                                         @Valid @ModelAttribute DiseaseStatusSaveForm diseaseStatusSaveForm,
                                         BindingResult bindingResult)throws IOException {
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        DiseaseStatusResponse diseaseStatusResponse = diseaseStatusService.DiseaseToDiseaseResponse(diseaseStatusService.save(petIdx, diseaseStatusSaveForm));

        return assembler.toModel(diseaseStatusResponse);
    }

    @PatchMapping("/{diseaseIdx}")
    public EntityModel<DiseaseStatusResponse> updateDiseaseStatus(@PathVariable(name = "petIdx")Long petIdx,
                                      @PathVariable(name="diseaseIdx")Long diseaseIdx,
                                      @Valid @ModelAttribute DiseaseStatusUpdateForm diseaseStatusUpdateForm,
                                      BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        DiseaseStatusResponse diseaseStatusResponse = diseaseStatusService.DiseaseToDiseaseResponse(diseaseStatusService.update(petIdx, diseaseIdx, diseaseStatusUpdateForm));

        return assembler.toModel(diseaseStatusResponse);
    }

    @DeleteMapping("/{diseaseIdx}")
    public DeleteResult deleteDiseaseStatus(@PathVariable(name = "petIdx")Long petIdx,
                                            @PathVariable(name = "diseaseIdx")Long diseaseIdx){
        diseaseStatusService.delete(petIdx, diseaseIdx);

        return new DeleteResult(200, "반려동물의 질환정보가 삭제되었습니다.");
    }
}
