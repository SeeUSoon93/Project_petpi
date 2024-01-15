package com.soon.petpi.service;

import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusRequest;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusResponse;
import com.soon.petpi.model.dto.DiseaseStatus.request.DiseaseStatusSaveForm;
import com.soon.petpi.model.dto.DiseaseStatus.request.DiseaseStatusUpdateForm;
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

    public DiseaseStatus save(Long petIdx, DiseaseStatusSaveForm diseaseStatusSaveForm) throws IOException {

        Pet pet = findOnePet(petIdx);
        if(pet == null){
            return null;
        }

        DiseaseStatus d = diseaseRequestToDisease(diseaseStatusSaveForm);
        d.setPet(pet);

        diseaseStatusRepository.save(d);
        return d;
    }

    public Pet findOnePet(Long petIdx) {
        return petRepository.findById(petIdx).orElse(null);
    }

    public DiseaseStatus diseaseRequestToDisease(DiseaseStatusSaveForm diseaseStatusSaveForm) throws IOException {
        return DiseaseStatus.builder()
                .diseaseDate(diseaseStatusSaveForm.getDiseaseDate())
                .diseaseName(diseaseStatusSaveForm.getDiseaseName())
                .diseaseLabel(diseaseStatusSaveForm.getDiseaseLabel())
                .build();
    }

    public DiseaseStatus update(Long petIdx, Long diseaseIdx, DiseaseStatusUpdateForm diseaseStatusUpdateForm){

        DiseaseStatus d = findOne(petIdx, diseaseIdx);

        log.info(diseaseStatusUpdateForm.toString());

        if(diseaseStatusUpdateForm.getDiseaseName() != null){
            d.setDiseaseName(diseaseStatusUpdateForm.getDiseaseName());
        }
        if(diseaseStatusUpdateForm.getDiseaseLabel() != null){
            d.setDiseaseLabel(diseaseStatusUpdateForm.getDiseaseLabel());
        }

        return diseaseStatusRepository.save(d);
    }

    public DiseaseStatus findOne(Long petIdx, Long diseaseIdx){
        return diseaseStatusRepository.findByIdAndPetIdx(petIdx, diseaseIdx).orElse(null);
    }

    public Boolean delete(Long petIdx, Long diseaseIdx) {

        DiseaseStatus deleteD = findOne(petIdx, diseaseIdx);

        if(deleteD == null){
            return false;
        }

        diseaseStatusRepository.delete(deleteD);

        return true;
    }

    public DiseaseStatusResponse DiseaseToDiseaseResponse(DiseaseStatus diseaseStatus) {
        return DiseaseStatusResponse.builder()
                .diseaseIdx(diseaseStatus.getDiseaseIdx())
                .diseaseDate(diseaseStatus.getDiseaseDate())
                .diseaseName(diseaseStatus.getDiseaseName())
                .diseaseLabel(diseaseStatus.getDiseaseLabel())
                .build();
    }

}
