package com.soon.petpi.repository;

import com.soon.petpi.model.entity.Chat;
import com.soon.petpi.model.entity.ChatContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatContentRepository extends JpaRepository<ChatContent, Long> {
    @Query("select c from ChatContent c " +
            "where c.chat.chatIdx = :chatIdx")
    Optional<List<ChatContent>> findByChatIdx(@Param("chatIdx") Long chatIdx);

}
