package com.koreait.myBoot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.koreait.myBoot.entity.PostBookmark;
import com.koreait.myBoot.service.BookmarkQueryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkQueryService bookmarkQueryService;

    @GetMapping("/mypage/bookmarks")
    public String myBookmarks(@AuthenticationPrincipal UserDetails user, Model model) {
        List<PostBookmark> bookmarks = bookmarkQueryService.findBookmarksByUsername(user.getUsername());
        model.addAttribute("bookmarks", bookmarks);
        return "mypage-bookmarks";
    }
}
