package com.koreait.myBoot.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 로그인 실패 시 사유별로 쿼리파라미터를 다르게 붙여서 /login 으로 리다이렉트
 * login.js 가 이 값을 보고 "아이디 없음" / "비번 틀림" 같은 메시지를 뿌림
 */
@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        String errorType;

        if (exception instanceof UsernameNotFoundException) {
            errorType = "username";
        } else if (exception instanceof BadCredentialsException) {
            // BadCredentialsException 은 아이디 불일치/비밀번호 불일치 둘 다 올 수 있음
            // 대부분은 비밀번호 틀림 케이스로 사용자에게 보여주면 충분
            errorType = "password";
        } else {
            errorType = "unknown";
        }

        // ✅ UserController가 읽는 키로 통일
        response.sendRedirect("/login?loginErrorType=" + errorType);
    }
}
