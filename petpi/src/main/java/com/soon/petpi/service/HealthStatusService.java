package com.soon.petpi.service;

import com.soon.petpi.exception.type.NoHealthStatusError;
import com.soon.petpi.model.dto.HealthStatus.HealthStatusResponse;
import com.soon.petpi.model.dto.HealthStatus.request.HealthStatusRequest;
import com.soon.petpi.model.dto.HealthStatus.request.HealthStatusSaveForm;
import com.soon.petpi.model.dto.HealthStatus.request.HealthStatusUpdateForm;
import com.soon.petpi.model.entity.HealthStatus;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.repository.HealthStatusRepository;
import com.soon.petpi.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthStatusService {

    private final HealthStatusRepository healthStatusRepository;
    private final PetRepository petRepository;

    public HealthStatus save(Long petIdx, HealthStatusSaveForm healthStatusSaveForm) throws IOException {

        Pet pet = findOnePet(petIdx);
        if(pet == null){
            return null;
        }

        HealthStatus h = healthRequestToHealth(healthStatusSaveForm);
        h.setPet(pet);

        healthStatusRepository.save(h);

        return h;
    }

    public Pet findOnePet(Long petIdx){
        return petRepository.findById(petIdx).orElse(null);
    }

    public HealthStatus findOne(Long petIdx, Long statusIdx){
        HealthStatus healthStatus = healthStatusRepository.findByIdAndPetIdx(petIdx, statusIdx).orElse(null);
        if(healthStatus == null){
            throw new NoHealthStatusError();
        }
        return healthStatus;
    }

    public HealthStatusResponse healthToHealthResponse(HealthStatus healthStatus){
        return HealthStatusResponse.builder()
                .statusIdx(healthStatus.getStatusIdx())
                .healthDate(healthStatus.getHealthDate())
                .petWeight(healthStatus.getPetWeight())
                .petPoo(healthStatus.getPetPoo())
                .petPee(healthStatus.getPetPee())
                .build();
    }

    public HealthStatus healthRequestToHealth(HealthStatusRequest healthStatusRequest) throws IOException{
        return HealthStatus.builder()
                .healthDate(healthStatusRequest.getHealthDate())
                .petWeight(healthStatusRequest.getPetWeight())
                .petPoo(healthStatusRequest.getPetPoo())
                .petPee(healthStatusRequest.getPetPee())
                .build();
    }

    public HealthStatus update(Long petIdx, Long statusIdx, HealthStatusUpdateForm healthStatusUpdateForm){

        HealthStatus h = findOne(petIdx, statusIdx);

        if(h == null){
            return null;
        }

        log.info(healthStatusUpdateForm.toString());

        if(healthStatusUpdateForm.getPetWeight() != 0){
            h.setPetWeight(healthStatusUpdateForm.getPetWeight());
        }
        if(healthStatusUpdateForm.getPetPoo() != null){
            h.setPetPoo(healthStatusUpdateForm.getPetPoo());
        }
        if(healthStatusUpdateForm.getPetPee() != null){
            h.setPetPee(healthStatusUpdateForm.getPetPee());
        }

        return healthStatusRepository.save(h);
    }

    public Boolean delete(Long petIdx, Long statusIdx){

        HealthStatus deleteH = findOne(petIdx, statusIdx);

        if(deleteH==null){
            return false;
        }

        healthStatusRepository.delete(deleteH);

        return true;
    }

}
