package com.koreait.myBoot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * 홈 대시보드/퀵액션에 필요한 가벼운 카운트 조회용 서비스.
 * 현재는 저장소 스키마 정보가 없어 안전한 "목업 값"을 반환합니다.
 * 실제 값으로 바꾸려면 TODO 위치에 Repository 쿼리를 넣으세요.
 */
@Service
@RequiredArgsConstructor
public class HomeStatsService {

    // TODO(확실하지 않음): 스키마를 모름. 필요 시 아래처럼 주입받아 사용하세요.
    // private final StudyPostRepository studyPostRepository;
    // private final ApplicationRepository applicationRepository;
    // private final StudyMemberRepository studyMemberRepository;
    // private final BookmarkRepository bookmarkRepository;

    /**
     * 오늘 새로 열린 스터디 팀 수
     */
    public long countTodayNewTeams() {
        // TODO(모르겠습니다): createdAt 필드/엔티티 구조를 몰라 실제 쿼리를 못 씀.
        // 예시:
        // LocalDateTime start = LocalDate.now().atStartOfDay();
        // LocalDateTime end   = start.plusDays(1);
        // return studyPostRepository.countByCreatedAtBetween(start, end);
        return 12L; // 데모 값
    }

    /**
     * 이번 주 참여 신청 수
     */
    public long countThisWeekApplications() {
        // TODO(모르겠습니다): 신청 엔티티/상태 필드 미확인.
        // 예시:
        // LocalDate now = LocalDate.now();
        // LocalDate startOfWeek = now.with(java.time.DayOfWeek.MONDAY);
        // LocalDateTime start = startOfWeek.atStartOfDay();
        // LocalDateTime end   = start.plusDays(7);
        // return applicationRepository.countByCreatedAtBetween(start, end);
        return 87L; // 데모 값
    }

    /**
     * 내가 참여 중인(진행중) 스터디 수
     */
    public long countMyActiveStudies(String username) {
        // TODO(모르겠습니다): 멤버십/상태 스키마 불명.
        // 예시:
        // return studyMemberRepository.countByUsernameAndStatus(username, ACTIVE);
        return 3L; // 데모 값
    }

    /**
     * 내 북마크 수
     */
    public long countMyBookmarks(String username) {
        // TODO(모르겠습니다): 북마크 테이블/관계 미확인.
        // 예시:
        // return bookmarkRepository.countByUsername(username);
        return 5L; // 데모 값
    }
}
