package com.koreait.myBoot.dto;

import com.koreait.myBoot.entity.StudyPost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StudyPostDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String author;          // 화면에 보여줄 작성자명
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 엔티티 -> DTO 변환
    public static StudyPostDetailResponse fromEntity(StudyPost post) {
        return StudyPostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())          // ✅ 여기 확정
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
