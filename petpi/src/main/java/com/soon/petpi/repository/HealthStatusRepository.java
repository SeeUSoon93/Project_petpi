package com.soon.petpi.repository;

import com.soon.petpi.model.entity.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {
}
