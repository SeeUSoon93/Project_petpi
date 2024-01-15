package com.soon.petpi.service.assembler;

import com.soon.petpi.controller.DiseaseStatusController;
import com.soon.petpi.model.dto.DiseaseStatus.DiseaseStatusResponse;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DiseaseStatusResponseAssembler implements RepresentationModelAssembler<DiseaseStatusResponse, EntityModel<DiseaseStatusResponse>> {

    @Override
    @SneakyThrows
    public EntityModel<DiseaseStatusResponse> toModel(DiseaseStatusResponse entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(DiseaseStatusController.class).updateDiseaseStatus(entity.getPetIdx(), entity.getDiseaseIdx(), null, null)).withRel("update"),
                linkTo(methodOn(DiseaseStatusController.class).deleteDiseaseStatus(entity.getPetIdx(), entity.getDiseaseIdx())).withRel("delete"),
                Link.of("https://petpi-project.gitbook.io/petpi-api-spec/").withRel("Docs")
        );
    }
}
