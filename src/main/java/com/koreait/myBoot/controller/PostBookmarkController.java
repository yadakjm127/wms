package com.koreait.myBoot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.koreait.myBoot.service.PostBookmarkService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostBookmarkController {

    private final PostBookmarkService postBookmarkService;

    @PostMapping("/{id}/bookmark")
    public ResponseEntity<?> toggleBookmark(@PathVariable Long id,
                                            @AuthenticationPrincipal UserDetails user) {
        boolean bookmarked = postBookmarkService.toggleBookmark(id, user.getUsername());
        long count = postBookmarkService.countBookmarks(id);

        return ResponseEntity.ok().body(
                new java.util.HashMap<>() {{
                    put("bookmarked", bookmarked);
                    put("count", count);
                }}
        );
    }

    @GetMapping("/{id}/bookmarks")
    public ResponseEntity<Long> countBookmarks(@PathVariable Long id) {
        return ResponseEntity.ok(postBookmarkService.countBookmarks(id));
    }
}
