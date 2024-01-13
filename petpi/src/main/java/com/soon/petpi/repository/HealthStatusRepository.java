package com.soon.petpi.repository;

import com.soon.petpi.model.entity.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {

    @Query("select h from HealthStatus h " +
            "where h.statusIdx = :statusIdx and h.pet.petIdx = :petIdx")
    Optional<HealthStatus> findByIdAndPetIdx(@Param("petIdx")Long petIdx, @Param("statusIdx")Long statusIdx);
}
