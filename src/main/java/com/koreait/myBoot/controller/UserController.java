package com.koreait.myBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    // ✅ 회원가입/로그인 매핑 금지 (중복 방지)

    // 마이페이지 예시
    @GetMapping("/profile")
    public String profile(@RequestParam(required = false) String tab, Model model) {
        model.addAttribute("tab", (tab == null ? "overview" : tab));
        return "user-profile"; // 필요시 템플릿 추가
    }
}
