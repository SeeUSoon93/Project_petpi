package com.soon.petpi.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ChatSaveDTO {
    @JsonProperty("chatSave")
    private ChatSave chatSave;

    @Data
    public static class ChatSave{
        @JsonProperty("petIdx")
        private Long petIdx;

        @JsonProperty("chatContent")
        private List<ChatContent> chatContent;
    }

    @Data
    public static class ChatContent{
        @JsonProperty("Q")
        private String Q;

        @JsonProperty("A")
        private String A;
    }
}
