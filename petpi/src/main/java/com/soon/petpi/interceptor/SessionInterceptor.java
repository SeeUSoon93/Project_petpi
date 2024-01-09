package com.soon.petpi.interceptor;

import com.soon.petpi.exception.type.SessionError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new SessionError("로그인되지 않은 사용자입니다");
        }

        log.info("session = {}", session);

        return true;
    }
}
