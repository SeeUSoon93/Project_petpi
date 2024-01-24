package com.soon.petpi.model.dto.chat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatUpdateDTO {
    @JsonProperty("chatIdx")
    private Long chatIdx;
    @JsonProperty("chatContent")
    private List<ChatSaveDTO.ChatContent> chatContents;
}
