package com.soon.petpi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soon.petpi.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/openai")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat-gpt")
    public ResponseEntity<String> chatGptQuestion(@RequestBody String message) throws JsonProcessingException {
        ResponseEntity<String> response = chatService.chatGptAnswer(message);
        return response;
    }



}
