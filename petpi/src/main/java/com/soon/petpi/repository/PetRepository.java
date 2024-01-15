package com.soon.petpi.repository;

import com.soon.petpi.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p " +
            "where p.user.userIdx = :userIdx")
    Optional<List<Pet>> findByUserIdx(@Param("userIdx")Long userIdx);

    @Query("select p from Pet p " +
            "join fetch p.healthStatuses h " +
            "where p.petIdx =:petIdx and p.user.userIdx = :userIdx")
    Optional<Pet> findByIdCalenderAndUserIdx(@Param("petIdx")Long petIdx, @Param("userIdx")Long userIdx);

    @Query("select p from Pet p " +
            "where p.petIdx = :petIdx and p.user.userIdx = :userIdx")
    Optional<Pet> findByIdAndUserIdx(@Param("petIdx")Long petIdx, @Param("userIdx")Long userIdx);

}
