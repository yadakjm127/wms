package com.koreait.myBoot.controller;

import com.koreait.myBoot.service.HomeStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeStatsService homeStatsService;

    @GetMapping("/")  // ★ "/index" 제거 (상호 리다이렉트 차단)
    public String index(Authentication authentication, Model model) {
        model.addAttribute("todayNewTeams", homeStatsService.countTodayNewTeams());
        model.addAttribute("thisWeekApplications", homeStatsService.countThisWeekApplications());

        boolean isLogin = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isLogin", isLogin);

        long myActiveCount = 0L;
        long myBookmarks = 0L;
        if (isLogin) {
            String username = authentication.getName();
            myActiveCount = homeStatsService.countMyActiveStudies(username);
            myBookmarks  = homeStatsService.countMyBookmarks(username);
        }
        model.addAttribute("myActiveCount", myActiveCount);
        model.addAttribute("myBookmarks", myBookmarks);

        return "index";
    }
}
