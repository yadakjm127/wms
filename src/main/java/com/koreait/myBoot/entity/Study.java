package com.koreait.myBoot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "studies")
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 제목 */
    @Column(nullable = false, length = 120)
    private String title;

    /** 간단 소개 */
    @Column(nullable = false, length = 500)
    private String intro;

    /** 뱃지(모집중/마감임박/신규 등 간단 라벨) */
    @Column(length = 40)
    private String badge;

    /** 참여 인원수 */
    @Column(nullable = false)
    private int memberCount;

    /** 생성일 */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /** 카테고리(예: 개발, 디자인, 어학 등) */
    @Column(nullable = false, length = 40)
    private String category;

    // --- 생성자 ---
    protected Study() {}

    public Study(String title, String intro, String badge, int memberCount, LocalDateTime createdAt, String category) {
        this.title = title;
        this.intro = intro;
        this.badge = badge;
        this.memberCount = memberCount;
        this.createdAt = createdAt;
        this.category = category;
    }

    // --- Getter/Setter ---
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getIntro() { return intro; }
    public String getBadge() { return badge; }
    public int getMemberCount() { return memberCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getCategory() { return category; }

    public void setTitle(String title) { this.title = title; }
    public void setIntro(String intro) { this.intro = intro; }
    public void setBadge(String badge) { this.badge = badge; }
    public void setMemberCount(int memberCount) { this.memberCount = memberCount; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCategory(String category) { this.category = category; }
}
