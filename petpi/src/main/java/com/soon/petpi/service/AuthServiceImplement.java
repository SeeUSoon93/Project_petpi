package com.soon.petpi.service;

import com.soon.petpi.model.dto.user.ResponseDto;
import com.soon.petpi.model.dto.user.auth.*;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.provider.JwtProvider;
import com.soon.petpi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtProvider jwtProvider;
    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try {
            String userEmail = dto.getEmail();
            boolean isExistEmail = userRepository.existsByUserEmail(userEmail);
            if (isExistEmail) return IdCheckResponseDto.duplicateId();
        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {

            String userEmail = dto.getEmail();
            boolean isExistEmail = userRepository.existsByUserEmail(userEmail);
            if (isExistEmail) return SignUpResponseDto.duplicateId();

            String password = dto.getPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            User user = new User(dto);
            userRepository.save(user);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String token = null;

        try {
            String userEmail = dto.getEmail();
            User user = userRepository.findByUserEmail(userEmail);
            if (user == null) return SignInResponseDto.SignInFail();

            String password = dto.getPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            boolean isMatched = bCryptPasswordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.SignInFail();

            token = jwtProvider.create(user);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(token);
    }
}
