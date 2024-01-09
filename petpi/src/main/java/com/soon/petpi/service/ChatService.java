package com.soon.petpi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon.petpi.argumentresolver.Login;
import com.soon.petpi.model.dto.chat.Message;
import com.soon.petpi.model.entity.Chat;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.repository.ChatRepository;
import com.soon.petpi.repository.PetRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    @Value("${openai.api-key}")
    private String apikey;

    @Value("${openai.model}")
    private String model;

    private final ChatRepository chatRepository;
    private final PetRepository petRepository;

    // pet 객체가 있는 지 없는 지 판단하는 로직
    public void petExeption(HttpSession session){
        session.getId();
    }

    // 상담내역 시작(chat gpt api)
    public ResponseEntity<String> chatGptAnswer(String content) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        // json형식의 매개변수 message 역직렬화를 통해 String 데이터 타입으로 변환(Object활용)
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(content);
        content = jsonNode.get("message").asText();
        log.info("content = {}", content); // log찍어보기

        // 엔드포인트 uri 지정
        String uri = "https://api.openai.com/v1/chat/completions";

        // 헤드 작성 + apikey
        httpHeaders.add("Authorization", "Bearer " + apikey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); // json타입으로 데이터를 전달하겠다는 뜻

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("user", content));

        // 바디 작성 + model + messages
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);

        // 요청하고 받기
        RequestEntity<Map<String, Object>> httpEntity
                = new RequestEntity<>(requestBody, httpHeaders, HttpMethod.POST, URI.create(uri));

        ResponseEntity<String> exchange
                = restTemplate.exchange(httpEntity, String.class);

        return exchange;
    }

    // 상담내역 저장(save)
    public boolean chatSaveService(String chatMessage){
        try {
            Chat chat = Chat.builder()
                    .chatDate(LocalDate.now())
                    .chatContent(chatMessage)
                    //.pet()
                    .build();
            chatRepository.save(chat);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    // 상담내역 불러오기(read)

    // 상담내역 삭제(delete)
}
