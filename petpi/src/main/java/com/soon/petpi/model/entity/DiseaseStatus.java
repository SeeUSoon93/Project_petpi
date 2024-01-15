package com.soon.petpi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soon.petpi.model.label.DiseaseLabel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
public class DiseaseStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long diseaseIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_idx")
    @JsonIgnore
    private Pet pet;

    @Column(updatable = false)
    private LocalDate diseaseDate;

    private String diseaseName;

    @Enumerated(EnumType.STRING)
    private DiseaseLabel diseaseLabel;

    @Builder
    public DiseaseStatus(Long diseaseIdx, Pet pet, LocalDate diseaseDate,
                         String diseaseName, DiseaseLabel diseaseLabel) {
        this.diseaseIdx = diseaseIdx;
        this.pet = pet;
        this.diseaseDate = diseaseDate;
        this.diseaseName = diseaseName;
        this.diseaseLabel = diseaseLabel;
    }

    public String getDiseaseLabel() {
        return diseaseLabel.getLabelName();
    }
}
