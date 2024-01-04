package com.soon.petpi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soon.petpi.model.label.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userIdx;

    private String userEmail;

    private String userPw;

    private String userNick;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Pet> pets;

    @Builder
    public User(Long userIdx, String userEmail, String userNick, String userPw, UserRole userRole) {
        this.userIdx = userIdx;
        this.userEmail = userEmail;
        this.userNick = userNick;
        this.userRole = userRole;
        this.userPw = userPw;
    }

    public String getUserRole() {
        return userRole.getRoleName();
    }
}
