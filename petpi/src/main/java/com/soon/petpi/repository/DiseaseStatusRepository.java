package com.soon.petpi.repository;

import com.soon.petpi.model.entity.DiseaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DiseaseStatusRepository extends JpaRepository<DiseaseStatus, Long> {
    @Query("select d from DiseaseStatus d " +
            "where d.diseaseIdx = :diseaseIdx and d.pet.petIdx = :petIdx")
    Optional<DiseaseStatus> findByIdAndPetIdx(@Param("petIdx") Long petIdx, @Param("diseaseIdx")Long diseaseIdx);
}
