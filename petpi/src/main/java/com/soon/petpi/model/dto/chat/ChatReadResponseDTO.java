package com.soon.petpi.model.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ChatReadResponseDTO {

    private String petName;
    private Long chatIdx;
    private LocalDate chatDate;
    private List<ChatContent> chatContentList;

    @Data
    @AllArgsConstructor
    public static class ChatContent{
        private String question;
        private String answer;
    }

    @Builder
    public ChatReadResponseDTO(String petName, Long chatIdx, LocalDate chatDate, List<ChatContent> chatContentList) {
        this.petName = petName;
        this.chatIdx = chatIdx;
        this.chatDate = chatDate;
        this.chatContentList = chatContentList;
    }
}
