package com.koreait.myBoot.service;

import com.koreait.myBoot.dto.StudyPostCreateRequest;
import com.koreait.myBoot.dto.StudyPostDetailResponse;
import com.koreait.myBoot.dto.StudyPostListResponse;
import com.koreait.myBoot.dto.StudyPostUpdateRequest;
import com.koreait.myBoot.entity.StudyPost;
import com.koreait.myBoot.repository.StudyPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyPostService {

    private final StudyPostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<StudyPostListResponse> list(
            String category,
            String q,
            int page,
            int size,
            String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<StudyPost> result = postRepository.findAll(pageable);
        return result.map(StudyPostListResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public StudyPostDetailResponse getDetail(Long id) {
        StudyPost post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id=" + id));
        return StudyPostDetailResponse.fromEntity(post);
    }

    public Long create(StudyPostCreateRequest req, String username) {
        String authorName = (username != null && !username.isBlank()) ? username : "anonymous";
        StudyPost post = StudyPost.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .author(authorName)
                .build();
        return postRepository.save(post).getId();
    }

    /** 작성자 본인인지 검사 (아니면 AccessDeniedException) */
    @Transactional(readOnly = true)
    public void ensureOwner(Long postId, String currentUsername) {
        StudyPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id=" + postId));
        if (currentUsername == null || !post.getAuthor().equals(currentUsername)) {
            throw new AccessDeniedException("작성자만 접근할 수 있습니다.");
        }
    }

    /** 수정 (작성자만) */
    public void update(Long id, StudyPostUpdateRequest req, String currentUsername) {
        StudyPost post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id=" + id));
        if (currentUsername == null || !post.getAuthor().equals(currentUsername)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        // updatedAt 은 @PreUpdate 로 자동 갱신
    }

    /** 삭제 (작성자만) */
    public void delete(Long id, String currentUsername) {
        StudyPost post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id=" + id));
        if (currentUsername == null || !post.getAuthor().equals(currentUsername)) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
    }
}
