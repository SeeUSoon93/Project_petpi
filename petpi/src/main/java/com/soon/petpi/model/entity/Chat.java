package com.soon.petpi.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat")
public class Chat {
    /**
     * chat GPT API를 연결하고 상담 내역을 DB에 저장할 엔터티입니다.
     * 변수는 다음과 같습니다.
     * chatIdx = PK를 담당할 idx번호
     * chatDate = 언제 상담을 했는 지 확인 가능한 날짜 데이터
     * chatContent = 상담내역 데이터
     * pet = FK로 받아올 pet데이터
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatIdx;

    private LocalDateTime chatDate;

    private String chatContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;

    @Builder
    public Chat(Long chatIdx, LocalDateTime chatDate, String chatContent, Pet pet) {
        this.chatIdx = chatIdx;
        this.chatDate = chatDate;
        this.chatContent = chatContent;
        this.pet = pet;
    }
}
