package com.koreait.myBoot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "registered", required = false) String registered,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (registered != null) model.addAttribute("registered", true);
        if (error != null)      model.addAttribute("error", true);
        if (logout != null)     model.addAttribute("logout", true);
        return "login";
    }

    // 회원가입 폼
    @GetMapping("/users/register")
    public String registerForm(@RequestParam(value = "from", required = false) String from, Model model) {
        model.addAttribute("from", from);
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/users/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "nickname", required = false) String nickname,
                           BindingResult bindingResult,
                           Model model) {

        // 간단 검증 (데모)
        if (username == null || username.isBlank()) {
            model.addAttribute("msg", "아이디를 입력하세요.");
            return "register";
        }
        if (password == null || password.isBlank()) {
            model.addAttribute("msg", "비밀번호를 입력하세요.");
            return "register";
        }

        // TODO: 사용자 저장 로직 (중복아이디 체크, 비밀번호 인코딩 등)

        return "redirect:/login?registered";
    }
}
