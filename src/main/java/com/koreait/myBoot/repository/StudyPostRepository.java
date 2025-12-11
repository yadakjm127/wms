package com.koreait.myBoot.repository;

import com.koreait.myBoot.entity.StudyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {
    Page<StudyPost> findByAuthor(String author, Pageable pageable);
    long countByAuthor(String author);        // ← 내 글 수 카운트 용
}
