package com.soon.petpi.controller;

import com.soon.petpi.exception.type.FieldErrorException;
import com.soon.petpi.model.dto.DeleteResult;
import com.soon.petpi.model.dto.HealthStatus.HealthStatusResponse;
import com.soon.petpi.model.dto.HealthStatus.request.HealthStatusSaveForm;
import com.soon.petpi.model.dto.HealthStatus.request.HealthStatusUpdateForm;
import com.soon.petpi.service.HealthStatusService;
import com.soon.petpi.service.assembler.HealthStatusResponseAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user/pets/{petIdx}/health-status")
public class HealthStatusController {

    private final HealthStatusService healthStatusService;
    private final HealthStatusResponseAssembler assembler;

    @PostMapping()
    public EntityModel<HealthStatusResponse> saveHealthStatus(@PathVariable(name = "petIdx") Long petIdx,
                                                              @Valid @ModelAttribute HealthStatusSaveForm healthStatusSaveForm, BindingResult bindingResult)throws IOException {
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        HealthStatusResponse healthStatusResponse = healthStatusService.healthToHealthResponse((healthStatusService.save(petIdx, healthStatusSaveForm)));
        return assembler.toModel(healthStatusResponse);
    }

    @PatchMapping("/{statusIdx}")
    public EntityModel<HealthStatusResponse> updateHealthStatus(@PathVariable(name = "petIdx")Long petIdx,
                                                                @PathVariable(name="statusIdx")Long statusIdx,
                                                                @Valid @ModelAttribute HealthStatusUpdateForm healthStatusUpdateForm, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            throw new FieldErrorException(bindingResult);
        }

        HealthStatusResponse healthStatusResponse = healthStatusService.healthToHealthResponse((healthStatusService.update(petIdx, statusIdx, healthStatusUpdateForm)));

        return assembler.toModel(healthStatusResponse);
    }

    @DeleteMapping("/{statusIdx}")
    public DeleteResult deleteHealthStatus(@PathVariable(name = "petIdx") Long petIdx,
            @PathVariable(name = "statusIdx")Long statusIdx){

        healthStatusService.delete(petIdx, statusIdx);

        return new DeleteResult(200, "반려동물의 건강상태 정보가 삭제되었습니다.");
    }
}
