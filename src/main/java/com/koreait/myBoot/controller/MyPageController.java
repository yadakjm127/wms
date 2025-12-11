package com.koreait.myBoot.controller;

import com.koreait.myBoot.dto.PasswordChangeRequest;
import com.koreait.myBoot.dto.ProfileUpdateRequest;
import com.koreait.myBoot.entity.User;
import com.koreait.myBoot.service.MyPageService;
import com.koreait.myBoot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MyPageController {

    private final UserService userService;
    private final MyPageService myPageService; // 추가

    @GetMapping("/mypage")
    public String myPage(@RequestParam(value = "tab", required = false, defaultValue = "profile") String tab,
                         Model model,
                         Principal principal) {
        String username = principal.getName();
        User me = userService.getByUsername(username);

        // 📊 요약 카드 데이터
        long myPostCount = userService.countMyPosts(username);
        long bookmarkCount = myPageService.countUserBookmarks(username);
        long likeCount = myPageService.countUserLikes(username);

        model.addAttribute("me", me);
        model.addAttribute("summary_myPosts", myPostCount);
        model.addAttribute("summary_bookmarks", bookmarkCount);
        model.addAttribute("summary_likes", likeCount);
        model.addAttribute("tab", tab);

        // 폼 기본값
        ProfileUpdateRequest profile = new ProfileUpdateRequest();
        profile.setName(me.getName());
        profile.setEmail(me.getEmail());
        profile.setNickname(me.getNickname());
        profile.setBio(me.getBio());
        profile.setAvatarUrl(me.getAvatarUrl());
        model.addAttribute("profile", profile);
        model.addAttribute("pwForm", new PasswordChangeRequest());

        return "mypage";
    }

    @PostMapping("/mypage/profile")
    public String updateProfile(@Valid @ModelAttribute("profile") ProfileUpdateRequest req,
                                BindingResult binding,
                                Model model,
                                Principal principal) {
        String username = principal.getName();

        if (binding.hasErrors()) {
            return backToMyPageWithErrors(model, username, "profile", binding, req, null);
        }

        try {
            userService.updateProfile(username, req);
            model.addAttribute("successMessage", "프로필이 저장되었습니다.");
        } catch (IllegalArgumentException ex) {
            binding.reject("profileError", ex.getMessage());
        } catch (Exception ex) {
            binding.reject("profileError", "저장 중 오류가 발생했습니다.");
        }

        if (binding.hasErrors()) {
            return backToMyPageWithErrors(model, username, "profile", binding, req, null);
        }
        return "redirect:/mypage?tab=profile&saved";
    }

    @PostMapping("/mypage/password")
    public String changePassword(@Valid @ModelAttribute("pwForm") PasswordChangeRequest req,
                                 BindingResult binding,
                                 Model model,
                                 Principal principal) {
        String username = principal.getName();

        if (binding.hasErrors()) {
            return backToMyPageWithErrors(model, username, "password", null, null, req);
        }

        try {
            userService.changePassword(username, req);
            model.addAttribute("successMessage", "비밀번호가 변경되었습니다. 다시 로그인하시길 권장합니다.");
        } catch (IllegalArgumentException ex) {
            binding.reject("passwordError", ex.getMessage());
        } catch (Exception ex) {
            binding.reject("passwordError", "변경 중 오류가 발생했습니다.");
        }

        if (binding.hasErrors()) {
            return backToMyPageWithErrors(model, username, "password", null, null, req);
        }
        return "redirect:/mypage?tab=password&changed";
    }

    private String backToMyPageWithErrors(Model model,
                                          String username,
                                          String tab,
                                          BindingResult profileErrors,
                                          ProfileUpdateRequest profile,
                                          PasswordChangeRequest pwForm) {
        User me = userService.getByUsername(username);
        model.addAttribute("me", me);
        model.addAttribute("tab", tab);
        model.addAttribute("summary_myPosts", userService.countMyPosts(username));
        model.addAttribute("summary_bookmarks", myPageService.countUserBookmarks(username));
        model.addAttribute("summary_likes", myPageService.countUserLikes(username));

        if (profile != null) model.addAttribute("profile", profile);
        if (pwForm != null) model.addAttribute("pwForm", pwForm);
        if (profileErrors != null) model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "profile", profileErrors);

        return "mypage";
    }
}
