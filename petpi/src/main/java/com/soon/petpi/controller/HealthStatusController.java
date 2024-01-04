package com.soon.petpi.controller;

import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.model.dto.HealthStatus.HealthStatusRequest;
import com.soon.petpi.model.dto.HealthStatus.HealthStatusResponse;
import com.soon.petpi.model.entity.HealthStatus;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.service.HealthStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/health")
public class HealthStatusController {

    private final HealthStatusService healthStatusService;

    @PostMapping("/save/{petIdx}")
    public HealthStatusResponse saveHealthStatus(@PathVariable(name = "petIdx") Long petIdx,
                               @Valid @RequestBody HealthStatusRequest healthStatusRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            return null;
        }
        return healthStatusService.healthToHealthResponse(healthStatusService.save(petIdx, healthStatusRequest));
    }

    @PatchMapping("/{statusIdx}")
    public HealthStatusResponse updateHealthStatus(@PathVariable(name = "statusIdx") Long statusIdx,
                                       @Valid @RequestBody HealthStatusRequest healthStatusRequest){
        return healthStatusService.healthToHealthResponse(healthStatusService.update(statusIdx, healthStatusRequest));
    }

    @DeleteMapping("{statusIdx}")
    public Boolean deleteHealthStatus(@PathVariable(name = "statusIdx")Long statusIdx){
        return healthStatusService.delete(statusIdx);
    }
}
