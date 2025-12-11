package com.koreait.myBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreait.myBoot.entity.PostBookmark;
import com.koreait.myBoot.entity.StudyPost;
import com.koreait.myBoot.entity.User;

import java.util.Optional;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    Optional<PostBookmark> findByPostAndUser(StudyPost post, User user);
    long countByPost(StudyPost post);
}
