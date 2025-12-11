package com.koreait.myBoot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.koreait.myBoot.entity.User;
import com.koreait.myBoot.repository.PostBookmarkRepository;
import com.koreait.myBoot.repository.PostLikeRepository;
import com.koreait.myBoot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostBookmarkRepository postBookmarkRepository;

    public long countUserLikes(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        // 좋아요는 사용자가 남긴 것보다 게시글 좋아요 합산을 보여주는 구조라면 별도 로직 필요
        // 일단 '내가 좋아요한 글' 수 기준으로 표시
        return postLikeRepository.findAll()
                .stream()
                .filter(like -> like.getUser().equals(user))
                .count();
    }

    public long countUserBookmarks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return postBookmarkRepository.findAll()
                .stream()
                .filter(bm -> bm.getUser().equals(user))
                .count();
    }
}
