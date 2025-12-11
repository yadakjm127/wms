package com.koreait.myBoot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.koreait.myBoot.entity.PostBookmark;
import com.koreait.myBoot.entity.User;
import com.koreait.myBoot.repository.PostBookmarkRepository;
import com.koreait.myBoot.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkQueryService {

    private final PostBookmarkRepository postBookmarkRepository;
    private final UserRepository userRepository;

    public List<PostBookmark> findBookmarksByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return postBookmarkRepository.findAll()
                .stream()
                .filter(b -> b.getUser().equals(user))
                .toList();
    }
}
