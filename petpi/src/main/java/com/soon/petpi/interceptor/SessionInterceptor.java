package com.soon.petpi.interceptor;

import com.soon.petpi.exception.type.SessionError;
import com.soon.petpi.model.label.SessionConst;
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
            throw new SessionError();
        }

        log.info("session [userIdx] = {}", session.getAttribute(SessionConst.USER_IDX));

        return true;
    }
}
