package com.koreait.myBoot.dto;

import com.koreait.myBoot.entity.StudyPost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StudyPostListResponse {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdAt;

    // 엔티티 -> DTO 변환용 정적 메서드
    public static StudyPostListResponse fromEntity(StudyPost post) {
        return StudyPostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
