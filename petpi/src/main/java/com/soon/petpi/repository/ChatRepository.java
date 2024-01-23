package com.soon.petpi.repository;

import com.soon.petpi.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("select c from Chat c " +
            "where c.pet.petIdx = :petIdx")
    Optional<List<Chat>> findByPetIdx(@Param("petIdx") Long petIdx);

}
