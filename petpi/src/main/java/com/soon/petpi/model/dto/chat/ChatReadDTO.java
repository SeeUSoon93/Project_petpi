package com.soon.petpi.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soon.petpi.model.entity.Pet;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ChatReadDTO {

    private List<ChatInfo> chatInfo;
    @Data
    public static class ChatInfo{
        private Long chatIdx;
        private LocalDate chatDate;
        private String chatContent;
        private Pet pet;
    }
}
