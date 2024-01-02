package com.soon.petpi.model.dto.pet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter @Setter
public class PetCalenderResponse {
    private String petName;
    private List<CalenderDiseaseStatuses> calenderDiseaseStatuses;
    private List<CalenderHealthStatuses> calenderHealthStatuses;

    @Builder
    @Getter @Setter
    public static class CalenderDiseaseStatuses {
        private LocalDate date;
        private String diseaseName;
        private String diseaseLabel;
    }
    @Builder
    @Getter @Setter
    public static class CalenderHealthStatuses {
        private LocalDate date;
        private int petWeight;
        private String petPoo;
        private String petPee;
    }
}
