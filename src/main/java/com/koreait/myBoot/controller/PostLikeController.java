package com.koreait.myBoot.controller;

import com.koreait.myBoot.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{id}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetails user) {
        boolean liked = postLikeService.toggleLike(id, user.getUsername());
        long count = postLikeService.countLikes(id);

        return ResponseEntity.ok().body(
                new java.util.HashMap<>() {{
                    put("liked", liked);
                    put("count", count);
                }}
        );
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<Long> countLikes(@PathVariable Long id) {
        return ResponseEntity.ok(postLikeService.countLikes(id));
    }
}
