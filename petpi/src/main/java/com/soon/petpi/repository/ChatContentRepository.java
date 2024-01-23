package com.soon.petpi.repository;

import com.soon.petpi.model.entity.ChatContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatContentRepository extends JpaRepository<ChatContent, Long> {
}
