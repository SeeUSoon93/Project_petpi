package com.soon.petpi.controller;

import com.soon.petpi.model.dto.dev.DevLoginRequest;
import com.soon.petpi.model.dto.dev.UserResponse;
import com.soon.petpi.model.entity.User;
import com.soon.petpi.model.label.SessionConst;
import com.soon.petpi.service.DevUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dev-user")
public class DevUserController {

    private final DevUserService devUserService;

    @PostMapping("/login")
    @ResponseBody
    public UserResponse devLogin(@Valid @RequestBody DevLoginRequest devLoginRequest, BindingResult bindingResult,
                      HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return null;
        }
    
        User loginUser = devUserService.login(devLoginRequest);
        if (loginUser == null) {
            return null;
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.USER_IDX, loginUser.getUserIdx());

        return devUserService.convertToUserResponse(loginUser);
    }

    @GetMapping("/kakao")
    public String kakaoMap() {
        return "map";
    }
}
