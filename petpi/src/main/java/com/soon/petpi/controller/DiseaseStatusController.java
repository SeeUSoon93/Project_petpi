package com.soon.petpi.controller;

import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.exception.type.FieldErrorException;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusRequest;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusResponse;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.service.DiseaseStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user/pets")
public class DiseaseStatusController {

    private final DiseaseStatusService diseaseStatusService;

    @PostMapping("/{petIdx}/disease-statuses")
    public DiseaseStatusResponse saveDiseaseStatus(@PathVariable(name = "petIdx") Long petIdx,
                                                   @Valid @RequestBody DiseaseStatusRequest diseaseStatusRequest, BindingResult bindingResult)throws IOException {
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        return diseaseStatusService.DiseaseToDiseaseResponse(diseaseStatusService.save(petIdx, diseaseStatusRequest));
    }

    @PatchMapping("/{petIdx}/disease-statuses/{diseaseIdx}")
    public DiseaseStatusResponse updateDiseaseStatus(@PathVariable(name="diseaseIdx")Long diseaseIdx,
                                                     @Valid @RequestBody DiseaseStatusRequest diseaseStatusRequest, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        return diseaseStatusService.DiseaseToDiseaseResponse(diseaseStatusService.update(diseaseIdx, diseaseStatusRequest));
    }

    @DeleteMapping("/{petIdx}/disease-statuses/{diseaseIdx}")
    public Boolean deleteDiseaseStatus(@PathVariable(name = "diseaseIdx")Long diseaseIdx){
        return diseaseStatusService.delete(diseaseIdx);
    }
}
