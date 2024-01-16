package com.soon.petpi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon.petpi.model.dto.chat.ChatSaveDTO;
import com.soon.petpi.model.dto.chat.Message;
import com.soon.petpi.model.entity.Chat;
import com.soon.petpi.model.entity.Pet;
import com.soon.petpi.repository.ChatRepository;
import com.soon.petpi.repository.PetRepository;
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

    /**
     *
     * @param content
     * @return
     * @throws JsonProcessingException
     */
    public ResponseEntity<String> chatGptAnswer(String content){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        // json형식의 매개변수 message 역직렬화를 통해 String 데이터 타입으로 변환(Object활용)
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

    /**
     * 상담내역 저장(save, create)
     * @param chatSave
     * @return
     */
    public Map<String, Object> chatSaveService(ChatSaveDTO chatSave){

        Map<String, Object> response = new HashMap<>();
        try {
            StringBuilder stringBuilder = new StringBuilder();

            // chatContent DB저장 전 세팅
            for(ChatSaveDTO.ChatContent chatContent : chatSave.getChatSave().getChatContent()){
                stringBuilder.append("Q:");
                stringBuilder.append(chatContent.getQ());
                stringBuilder.append("_");
                stringBuilder.append("A:");
                stringBuilder.append(chatContent.getA());
                stringBuilder.append("_");
            }

            // pet DB저장 전 세팅
            Pet pet = petRepository
                    .findById(chatSave.getChatSave().getPetIdx()).orElse(null);

            // build 패턴을 통한 chat(상담내역) DB저장
            Chat chat = Chat.builder()
                    .chatDate(LocalDate.now())
                    .chatContent(stringBuilder.toString())
                    .pet(pet)
                    .build();
            chatRepository.save(chat);

            response.put("message", "save success");
            return response;
        }catch (Exception e){
            response.put("message", "save fail");
            return response;
        }

    }

    /**
     * petName Map을 json으로 변환 후 반환
     * 추후 쳇상담 내역을 저장하기 전에 어떤 애완동물의
     * 상담내역인지 명시하기 위함.
     * @param userIdx
     * @return json데이터 반환 [{"petIdx" : value, "petName" : value}]
     */
    public Map<String, List<Map<String, Object>>> petNameList(Long userIdx){

        Optional<List<Pet>> petList = petRepository.findByUserIdx(userIdx);
        List<Map<String, Object>> petJson = new ArrayList<>();
        Map<String, List<Map<String, Object>>> petInfo = new HashMap<>();

        if (petList.isPresent()) {
            for (Pet pet : petList.get()) {
                Map<String, Object> successInfo = new HashMap<>();
                successInfo.put("petIdx", pet.getPetIdx());
                successInfo.put("petName", pet.getPetName());
                petJson.add(successInfo);
            }
        } else {
            Map<String, Object> failInfo = new HashMap<>();
            failInfo.put("message", "pet is null");
            petJson.add(failInfo);
        }
        petInfo.put("petInfo", petJson);
        return petInfo;
    }

    /**
     * 상담내역 불러오기(read)
     * @param userIdx
     * @return
     */
    public Map<String, Object> chatReadService(Long userIdx){
        Optional<List<Pet>> pet = petRepository.findByUserIdx(userIdx);
        List<Chat> chatList = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        if(pet.isPresent()){
            for(Pet userPet : pet.get()){
                Optional<List<Chat>> chat = chatRepository.findByPetIdx(userPet.getPetIdx());
                if(chat.isPresent()){
                    for(Chat userChat : chat.get()){
                        chatList.add(userChat);
                    }
                }
            }
            response.put("chatInfo", chatList);
        }else{
            response.put("message", "read error");
        }
        return response;
    }

    /**
     * 상담내역 삭제(delete)
     * @param chatIdx
     * @return
     */
    public Map<String, Object> chatDeleteService(Long chatIdx){
        log.info("chatIdx = {}",chatIdx);
        Map<String, Object> response = new HashMap<>();
        try {
            chatRepository.deleteById(chatIdx);
            response.put("message", "delete success");
        }catch(Exception e){
            response.put("message", "delete fail");
        }
        return response;
    }
}
