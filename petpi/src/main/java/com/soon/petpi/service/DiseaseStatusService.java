package com.soon.petpi.service;

import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusRequest;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusResponse;
import com.soon.petpi.model.entity.DiseaseStatus;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.repository.DiseaseStatusRepository;
import com.soon.petpi.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiseaseStatusService {

    private final DiseaseStatusRepository diseaseStatusRepository;
    private final PetRepository petRepository;

    public DiseaseStatus save(Long petIdx, DiseaseStatusRequest diseaseStatusRequest) throws IOException {

        Pet pet = findOnePet(petIdx);
        if(pet == null){
            return null;
        }

        DiseaseStatus d = diseaseRequestToDisease(diseaseStatusRequest);
        d.setDiseaseDate(LocalDate.now());
        d.setPet(pet);

        diseaseStatusRepository.save(d);
        return d;
    }

    public Pet findOnePet(Long petIdx) {
        return petRepository.findById(petIdx).orElse(null);
    }

    public DiseaseStatus diseaseRequestToDisease(DiseaseStatusRequest diseaseStatusRequest) throws IOException {
        return DiseaseStatus.builder()
                .diseaseName(diseaseStatusRequest.getDiseaseName())
                .diseaseLabel(diseaseStatusRequest.getDiseaseLabel())
                .build();
    }

    public DiseaseStatus update(Long diseaseIdx, DiseaseStatusRequest diseaseStatusRequest){

        DiseaseStatus d = findOne(diseaseIdx);

        if(d == null){
            return null;
        }

        d.setDiseaseName(diseaseStatusRequest.getDiseaseName());
        d.setDiseaseLabel(diseaseStatusRequest.getDiseaseLabel());
        return diseaseStatusRepository.save(d);
    }

    public DiseaseStatus findOne(Long diseaseIdx){
        return diseaseStatusRepository.findById(diseaseIdx).orElse(null);
    }

    public Boolean delete(Long diseaseIdx) {

        DiseaseStatus deleteD = findOne(diseaseIdx);

        if(deleteD == null){
            return false;
        }

        diseaseStatusRepository.delete(deleteD);

        return true;
    }

    public DiseaseStatusResponse DiseaseToDiseaseResponse(DiseaseStatus diseaseStatus) {
        return DiseaseStatusResponse.builder()
                .diseaseName(diseaseStatus.getDiseaseName())
                .diseaseLabel(diseaseStatus.getDiseaseLabel())
                .build();
    }

}
