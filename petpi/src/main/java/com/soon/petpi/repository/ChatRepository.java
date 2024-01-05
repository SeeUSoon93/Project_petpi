package com.soon.petpi.repository;

import com.soon.petpi.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
