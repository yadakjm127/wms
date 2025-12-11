package com.koreait.myBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreait.myBoot.entity.PostLike;
import com.koreait.myBoot.entity.StudyPost;
import com.koreait.myBoot.entity.User;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndUser(StudyPost post, User user);
    long countByPost(StudyPost post);
}
