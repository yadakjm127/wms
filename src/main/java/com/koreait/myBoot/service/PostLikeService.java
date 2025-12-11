package com.koreait.myBoot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreait.myBoot.entity.PostLike;
import com.koreait.myBoot.entity.StudyPost;
import com.koreait.myBoot.entity.User;
import com.koreait.myBoot.repository.PostLikeRepository;
import com.koreait.myBoot.repository.StudyPostRepository;
import com.koreait.myBoot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final StudyPostRepository studyPostRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean toggleLike(Long postId, String username) {
        StudyPost post = studyPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return postLikeRepository.findByPostAndUser(post, user)
                .map(like -> {
                    postLikeRepository.delete(like);
                    return false; // 좋아요 취소
                })
                .orElseGet(() -> {
                    postLikeRepository.save(new PostLike(null, post, user));
                    return true; // 좋아요 추가
                });
    }

    public long countLikes(Long postId) {
        StudyPost post = studyPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return postLikeRepository.countByPost(post);
    }
}
