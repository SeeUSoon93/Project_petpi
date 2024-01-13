package com.soon.petpi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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
    public boolean chatSave(@RequestBody String chatMessage, @Login Long userIdx){

        boolean discrimination = chatService.chatSaveService(chatMessage, userIdx);

        if(discrimination == true){
            return true;
        }else {
            return false;
        }
    }

    // @get(read) 상담내역 불러오기
    @GetMapping("/chat-read")
    public String chatRead(@Login Long userIdx){

        return "";
    }

    // @put or @patch는 생략

    // @delete 상담내역 삭제
    @GetMapping("/chat-delete")
    public String chatDelete(){
        return "";
    }


    @GetMapping("/test")
    public String test(@Login Long userIdx) throws JsonProcessingException {
        return chatService.petNameList(userIdx);
    }
}
