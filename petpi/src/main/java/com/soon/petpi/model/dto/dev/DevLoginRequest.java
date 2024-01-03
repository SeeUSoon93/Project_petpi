package com.soon.petpi.model.dto.dev;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DevLoginRequest {
    private String userEmail;
    private String userPw;
}
