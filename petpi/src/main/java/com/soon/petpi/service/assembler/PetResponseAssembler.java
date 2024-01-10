package com.soon.petpi.service.assembler;

import com.soon.petpi.controller.PetController;
import com.soon.petpi.model.dto.pet.PetResponse;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PetResponseAssembler implements RepresentationModelAssembler<PetResponse, EntityModel<PetResponse>> {
    @Override
    @SneakyThrows
    public EntityModel<PetResponse> toModel(PetResponse entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PetController.class).findAllPet(entity.getUserIdx())).withRel("collection"),
                linkTo(methodOn(PetController.class).findOnePet(entity.getUserIdx(), entity.getPetIdx())).withRel("detail"),
                linkTo(methodOn(PetController.class).updatePet(entity.getUserIdx(), entity.getPetIdx(), entity.getPetRequest(), entity.getBindingResult())).withRel("update"),
                linkTo(methodOn(PetController.class).deletePet(entity.getUserIdx(), entity.getPetIdx())).withRel("delete"),
                linkTo(methodOn(PetController.class).readCalender(entity.getUserIdx(), entity.getPetIdx())).withRel("readCalender"),
                Link.of("https://app.gitbook.com/o/q7cn1abVzBDpZmQuw6s2/s/N3YIzkli6HnrXECacyfi/").withRel("Docs")
        );
    }
}
