package com.soon.petpi.model.dto.chat;

import lombok.Data;

import java.util.List;

@Data
public class GptApiResponse {
    private List<Choices> choices;
    @Data
    public static class Choices{
        private Message message;
    }

    @Data
    public static class Message{
        private String content;
    }
}
