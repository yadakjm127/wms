package com.koreait.myBoot.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users",
       indexes = {@Index(name = "idx_users_username", columnList = "username", unique = true),
                  @Index(name = "idx_users_nickname", columnList = "nickname", unique = true)})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=50)
    private String username;

    @Column(nullable=false)
    private String password; // BCrypt

    @Column(nullable=false, length=100)
    private String name;

    @Column(nullable=false, length=100)
    private String email;

    @Column(nullable=false, length=30)
    private String role; // 예: ROLE_USER, ROLE_ADMIN

    // --- 프로필 확장 ---
    @Column(length = 40, unique = true)
    private String nickname;           // 화면 노출명 (선택, 중복 불가)

    @Column(length = 500)
    private String bio;                // 한 줄 소개/자기소개

    @Column(length = 255)
    private String avatarUrl;          // 아바타 이미지 경로(선택)

    private Boolean emailVerified;     // 이메일 인증 여부(간단 플래그)
    private LocalDateTime updatedAt;   // 최종 수정시각

    @PrePersist
    public void prePersist() {
        if (emailVerified == null) emailVerified = Boolean.FALSE;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
