package com.soon.petpi.controller;
import com.soon.petpi.argumentresolover.Login;
import com.soon.petpi.model.dto.chat.ChatReadResponseDTO;
import com.soon.petpi.model.dto.chat.ChatSaveDTO;
import com.soon.petpi.model.dto.chat.ChatUpdateDTO;
import com.soon.petpi.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/chat")
public class ChatController {

    private final ChatService chatService;

    // @post text 생성 gpt api 통신
    @PostMapping("/gpt")
    public String chatGptQuestion(@RequestBody String question){
        return chatService.chatGptAnswer(question);
    }

    // @post(create) 상담내역 저장(save)
    @PostMapping("/create")
    public Map<String, Object> chatSave(@RequestBody ChatSaveDTO chatSave){
        return chatService.chatSaveService(chatSave);
    }

    // @get(read) 상담내역 불러오기
    @GetMapping("/read")
    public Map<String, Object> chatRead(@Login Long userIdx){
        return chatService.chatReadService(userIdx);
    }

    // @post 상담내역 추가
    @PostMapping("/update")
    public Map<String,Object> chatUpdate(@RequestBody ChatUpdateDTO chatUpdate){
        return chatService.chatUpdateService(chatUpdate);
    }

    // @delete 상담내역 삭제
    @DeleteMapping("/delete/{chatIdx}")
    public Map<String, Object> chatDelete(@PathVariable("chatIdx") Long chatIdx){
        return chatService.chatDeleteService(chatIdx);
    }

    // @get 상담내역 저장 전 pet 선택
    @GetMapping("/choice")
    public Map<String, List<Map<String, Object>>> test(@Login Long userIdx){
        return chatService.petNameList(userIdx);
    }

}
