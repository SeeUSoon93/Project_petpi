package com.soon.petpi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.model.dto.chat.ChatSaveDTO;
import com.soon.petpi.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/openai")
public class ChatController {

    private final ChatService chatService;

    // @post api 통신
    @PostMapping("/chat-gpt")
    public ResponseEntity<String> chatGptQuestion(@RequestBody String message) throws JsonProcessingException {
        ResponseEntity<String> response = chatService.chatGptAnswer(message);
        return response;
    }

    // @post(create) 상담내역 저장(save)
    @PostMapping("/chat-save")
    public Map<String, Object> chatSave(@RequestBody ChatSaveDTO chatSave){

        return chatService.chatSaveService(chatSave);
    }

    // @get(read) 상담내역 불러오기
    @GetMapping("/chat-read")
    public Map<String, Object> chatRead(@Login Long userIdx){
        return chatService.chatReadService(userIdx);
    }

    // @put or @patch는 생략

    // @delete 상담내역 삭제
    @GetMapping("/chat-delete")
    public Map<String, Object> chatDelete(@RequestParam("chatIdx") Long chatIdx){
        return chatService.chatDeleteService(chatIdx);
    }

    @GetMapping("/chat-choice")
    public Map<String, List<Map<String, Object>>> test(@Login Long userIdx){
        return chatService.petNameList(userIdx);
    }
}
