package com.soon.petpi.repository;

import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmailAndUserPw(String userEmail, String UserPw);

    @Query("select u from User u " +
            "join fetch u.pets p " +
            "where u.id = :userIdx And p.id = :petIdx")
    Optional<User> findByIdAndPetIdx(Long userIdx, Long petIdx);
}
