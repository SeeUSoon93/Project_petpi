package com.soon.petpi.controller;

import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusRequest;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusResponse;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.service.DiseaseStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/disease")
public class DiseaseStatusController {

    private final DiseaseStatusService diseaseStatusService;

    @PostMapping("/save/{petIdx}")
    public DiseaseStatusResponse saveDiseaseStatus(@PathVariable(name = "petIdx") Long petIdx,
                                        @Valid @RequestBody DiseaseStatusRequest diseaseStatusRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            return null;
        }

        return diseaseStatusService.DiseaseToDiseaseCreateResponse(diseaseStatusService.save(petIdx, diseaseStatusRequest));
    }

    @PatchMapping("/{diseaseIdx}")
    public DiseaseStatusResponse updateDiseaseStatus(@PathVariable(name="diseaseIdx")Long diseaseIdx,
                                        @Valid @RequestBody DiseaseStatusRequest diseaseStatusRequest){
        return diseaseStatusService.DiseaseToDiseaseUpdateResponse(diseaseStatusService.update(diseaseIdx, diseaseStatusRequest));
    }

    @DeleteMapping("/{diseaseIdx}")
    public Boolean deleteDisease(@PathVariable(name = "diseaseIdx")Long diseaseIdx){
        return diseaseStatusService.delete(diseaseIdx);
    }
}
