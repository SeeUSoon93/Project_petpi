package com.soon.petpi.service.assembler;

import com.soon.petpi.controller.HealthStatusController;
import com.soon.petpi.model.dto.HealthStatus.HealthStatusResponse;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HealthStatusResponseAssembler implements RepresentationModelAssembler<HealthStatusResponse, EntityModel<HealthStatusResponse>> {

    @Override
    @SneakyThrows
    public EntityModel<HealthStatusResponse> toModel(HealthStatusResponse entity){
        return EntityModel.of(entity,
                linkTo(methodOn(HealthStatusController.class).updateHealthStatus(entity.getPetIdx(), entity.getStatusIdx(), null, null)).withRel("detail"),
                linkTo(methodOn(HealthStatusController.class).deleteHealthStatus(entity.getPetIdx(), entity.getStatusIdx())).withRel("delete"),
                Link.of("https://petpi-project.gitbook.io/petpi-api-spec/").withRel("Docs")
                );
    }
}
