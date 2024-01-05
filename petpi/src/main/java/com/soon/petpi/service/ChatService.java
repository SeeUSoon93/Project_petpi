package com.soon.petpi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon.petpi.model.dto.chat.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Slf4j
@Service
public class ChatService {

    @Value("${openai.api-key}")
    private String apikey;

    @Value("${openai.model}")
    private String model;

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


}
