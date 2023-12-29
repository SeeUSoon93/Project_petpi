package com.soon.petpi.service;

import com.soon.petpi.model.dto.dev.DevLoginRequest;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DevUserService {

    private final UserRepository userRepository;

    public User findUser(Long userIdx) {
        return userRepository.findById(userIdx).orElse(null);
    }

    public User login(DevLoginRequest devLoginRequest) {
        return userRepository.findByUserEmailAndUserPw(devLoginRequest.getUserEmail(), devLoginRequest.getUserPw())
                .orElse(null);
    }

}
