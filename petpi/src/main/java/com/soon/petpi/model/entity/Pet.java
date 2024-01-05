package com.soon.petpi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soon.petpi.model.label.PetGender;
import com.soon.petpi.model.label.PetSpecies;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pets")
public class Pet {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long petIdx;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @Enumerated(EnumType.STRING)
    private PetSpecies petSpecies;

    @Column(updatable = false)
    private LocalDate petBirthdate;

    @Enumerated(EnumType.STRING)
    private PetGender petGender;

    private String petName;

    private String petImage;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<HealthStatus> healthStatuses;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<DiseaseStatus> diseaseStatuses;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<Chat> chat;

    @Builder
    public Pet(Long petIdx, User user, PetSpecies petSpecies, PetGender petGender, String petName, String petImage,
               List<HealthStatus> healthStatuses, List<DiseaseStatus> diseaseStatuses, LocalDate petBirthdate, List<Chat> chat) {
        this.petIdx = petIdx;
        this.user = user;
        this.petSpecies = petSpecies;
        this.petBirthdate = petBirthdate;
        this.petGender = petGender;
        this.petName = petName;
        this.petImage = petImage;
        this.healthStatuses = healthStatuses;
        this.diseaseStatuses = diseaseStatuses;
        this.chat = chat;
    }

    public String getPetGender() {
        return petGender.getPetGender();
    }

    public String getPetSpecies() {
        return petSpecies.getSpeciesName();
    }
}
