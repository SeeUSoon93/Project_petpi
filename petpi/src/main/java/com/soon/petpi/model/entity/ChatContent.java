package com.soon.petpi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chatContents")
public class ChatContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatContentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_Idx")
    @JsonIgnore
    private Chat chat;

    private String question;

    private String answer;

    @Builder
    public ChatContent(Long chatContentIdx, Chat chat, String question, String answer) {
        this.chatContentIdx = chatContentIdx;
        this.chat = chat;
        this.question = question;
        this.answer = answer;
    }
}
