package com.soon.petpi.model.entity;

import com.soon.petpi.model.dto.user.auth.SignUpRequestDto;
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

    public User(SignUpRequestDto dto) {
        this.userEmail = dto.getEmail();
        this.userPw = dto.getPassword();
        this.userNick = dto.getNick();
        // to do 타입 필드가 있는 생성자 (일반, 카카오) this.type = "app" , this.type = "kakao"
        this.userRole = UserRole.ROLE_USER;

    }

    public String getUserRole() {
        return userRole.getRoleName();
    }

    public String getJwtRole() {
        return this.userRole.toString();
    }

}
