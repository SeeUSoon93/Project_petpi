package com.soon.petpi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    // 상담내역 시작(chat gpt api)
    @PostMapping("/chat-gpt")
    public ResponseEntity<String> chatGptQuestion(@RequestBody String message) throws JsonProcessingException {
        ResponseEntity<String> response = chatService.chatGptAnswer(message);
        return response;
    }

    // 상담내역 저장(save), JSON으로 데이터가 넘어온다는 가정
    @PostMapping("/chat-save")
    public boolean chatSave(@RequestBody String chatMessage){

        boolean discrimination = chatService.chatSaveService(chatMessage);

        if(discrimination == true){
            return true;
        }else {
            return false;
        }
    }

    // 상담내역 불러오기(read)
    @GetMapping("/chat-read")
    public String chatRead(){

        return "";
    }

    // 상담내역 삭제(delete)
    @GetMapping("/chat-delete")
    public String chatDelete(){
        return "";
    }

}
