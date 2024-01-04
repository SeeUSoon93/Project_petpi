package com.soon.petpi.model.dto.pet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter @Setter
public class PetCalenderResponse {
    private Long petIdx;
    private String petName;
    private List<CalenderDiseaseStatuses> calenderDiseaseStatuses;
    private List<CalenderHealthStatuses> calenderHealthStatuses;

    @Builder
    @Getter @Setter
    public static class CalenderDiseaseStatuses {
        private Long diseaseIdx;
        private LocalDate date;
        private String diseaseName;
        private String diseaseLabel;
    }
    @Builder
    @Getter @Setter
    public static class CalenderHealthStatuses {
        private Long statusIdx;
        private LocalDate date;
        private int petWeight;
        private String petPoo;
        private String petPee;
    }
}
