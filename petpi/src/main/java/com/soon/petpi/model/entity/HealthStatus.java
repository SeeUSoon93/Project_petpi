package com.soon.petpi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
public class HealthStatus {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long statusIdx;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_idx")
    private Pet pet;

    @Column(updatable = false)
    private LocalDate healthDate;

    private int petWeight;

    private String petPoo;

    private String petPee;

    @Builder
    public HealthStatus(Long statusIdx, Pet pet,
                        int petWeight, String petPoo, String petPee) {
        this.statusIdx = statusIdx;
        this.pet = pet;
        this.healthDate = LocalDate.now();
        this.petWeight = petWeight;
        this.petPoo = petPoo;
        this.petPee = petPee;
    }
}
