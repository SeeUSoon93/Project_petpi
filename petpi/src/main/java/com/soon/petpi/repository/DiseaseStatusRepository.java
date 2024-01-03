package com.soon.petpi.repository;

import com.soon.petpi.model.entity.DiseaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseStatusRepository extends JpaRepository<DiseaseStatus, Long> {
}
