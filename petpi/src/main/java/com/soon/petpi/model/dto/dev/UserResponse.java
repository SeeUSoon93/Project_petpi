package com.soon.petpi.model.dto.dev;

import com.soon.petpi.model.label.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String userEmail;
    private String userNick;
    private UserRole userRole;
}
