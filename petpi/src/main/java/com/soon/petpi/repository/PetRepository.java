package com.soon.petpi.repository;

import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @EntityGraph(attributePaths = {"healthStatuses"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select distinct p from Pet p where p.petIdx =:petIdx")
    Optional<Pet> findByIdEntityGraph(@Param("petIdx") Long petIdx);

    Optional<List<Pet>> findByUser(User user);

    @Query("select p, h from Pet p " +
            "join fetch p.healthStatuses h " +
            "where p.petIdx =:petIdx")
    Optional<Pet> findByIdCalender(@Param("petIdx")Long petIdx);

}
