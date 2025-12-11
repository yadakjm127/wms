package com.koreait.myBoot.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreait.myBoot.entity.PostBookmark;
import com.koreait.myBoot.entity.StudyPost;
import com.koreait.myBoot.entity.User;
import com.koreait.myBoot.repository.PostBookmarkRepository;
import com.koreait.myBoot.repository.StudyPostRepository;
import com.koreait.myBoot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostBookmarkService {

    private final PostBookmarkRepository postBookmarkRepository;
    private final StudyPostRepository studyPostRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean toggleBookmark(Long postId, String username) {
        StudyPost post = studyPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return postBookmarkRepository.findByPostAndUser(post, user)
                .map(bm -> {
                    postBookmarkRepository.delete(bm);
                    return false; // 북마크 취소
                })
                .orElseGet(() -> {
                    postBookmarkRepository.save(new PostBookmark(null, post, user));
                    return true; // 북마크 추가
                });
    }

    public long countBookmarks(Long postId) {
        StudyPost post = studyPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return postBookmarkRepository.countByPost(post);
    }
}
