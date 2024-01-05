package com.soon.petpi.service;

import com.soon.petpi.model.dto.HealthStatus.HealthStatusRequest;
import com.soon.petpi.model.dto.HealthStatus.HealthStatusResponse;
import com.soon.petpi.model.entity.HealthStatus;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.repository.HealthStatusRepository;
import com.soon.petpi.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthStatusService {

    private final HealthStatusRepository healthStatusRepository;
    private final PetRepository petRepository;

    public HealthStatus save(Long petIdx, HealthStatusRequest healthStatusRequest) throws IOException {

        Pet pet = findOnePet(petIdx);
        if(pet == null){
            return null;
        }

        HealthStatus h = healthRequestToHealth(healthStatusRequest);
        h.setHealthDate(LocalDate.now());
        h.setPet(pet);

        healthStatusRepository.save(h);

        return h;
    }

    public Pet findOnePet(Long petIdx){
        return petRepository.findById(petIdx).orElse(null);
    }

    public HealthStatus findOne(Long statusIdx){
        return healthStatusRepository.findById(statusIdx).orElse(null);
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
                .petWeight(healthStatusRequest.getPetWeight())
                .petPoo(healthStatusRequest.getPetPoo())
                .petPee(healthStatusRequest.getPetPee())
                .build();
    }

    public HealthStatus update(Long statusIdx, HealthStatusRequest healthStatusRequest){

        HealthStatus h = findOne(statusIdx);

        if(h == null){
            return null;
        }

        h.setPetWeight(healthStatusRequest.getPetWeight());
        h.setPetPoo(healthStatusRequest.getPetPoo());
        h.setPetPee(healthStatusRequest.getPetPee());

        return healthStatusRepository.save(h);
    }

    public Boolean delete(Long statusIdx){

        HealthStatus deleteH = findOne(statusIdx);

        if(deleteH==null){
            return false;
        }

        healthStatusRepository.delete(deleteH);

        return true;
    }

}
