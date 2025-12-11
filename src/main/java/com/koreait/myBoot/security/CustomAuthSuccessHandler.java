// src/main/java/com/koreait/myBoot/security/CustomAuthSuccessHandler.java
package com.koreait.myBoot.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import java.io.IOException;

/**
 * 로그인 성공 시 항상 홈("/")으로 보냄
 * SavedRequest(로그인 전 접근하려던 URL)도 비워서 무시한다.
 */
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // 로그인 전 저장된 원래 요청(URL) 제거
        requestCache.removeRequest(request, response);

        // 무조건 루트로
        response.sendRedirect("/");
    }
}
