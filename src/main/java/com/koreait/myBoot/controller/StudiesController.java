package com.koreait.myBoot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudiesController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    /** 스터디 찾기 (공개) */
    @GetMapping("/browse")
    public String browse(Model model,
                         @RequestParam(name = "q", required = false) String q,
                         @RequestParam(name = "category", required = false) String category) {

        model.addAttribute("q", q);
        model.addAttribute("category", category);
        model.addAttribute("categories", List.of("개발", "디자인", "어학"));

        // 데모 데이터 (createdAtText를 컨트롤러에서 미리 포맷)
        model.addAttribute("popularStudies", List.of(
                new StudyCard(101L, "토익 900+ 목표반", "모집중", "왕초보도 환영!", 5, fmt(LocalDate.now().minusDays(1))),
                new StudyCard(102L, "자바 개발자 취업 스터디", "마감임박", "알고리즘 & 프로젝트", 12, fmt(LocalDate.now().minusDays(3))),
                new StudyCard(103L, "UX 포트폴리오 피드백", "신규", "실무자와 함께", 3, fmt(LocalDate.now()))
        ));
        return "studies-browse"; // templates/studies-browse.html
    }

    /** 스터디 개설 폼 (로그인 필요) */
    @GetMapping("/create")
    public String createForm() {
        return "studies-create"; // templates/studies-create.html
    }

    /** 내 스터디 (로그인 필요) */
    @GetMapping("/my")
    public String myStudies(Model model, @AuthenticationPrincipal UserDetails user) {
        String username = (user != null) ? user.getUsername() : "guest";
        model.addAttribute("username", username);

        model.addAttribute("myStudies", List.of(
                new StudyCard(201L, "SQL 중급 스터디", "진행중", "주 2회", 8, fmt(LocalDate.now().minusWeeks(1))),
                new StudyCard(202L, "웹 기초 복습반", "종료", "HTML/CSS/JS", 10, fmt(LocalDate.now().minusWeeks(2)))
        ));
        return "studies-my"; // templates/studies-my.html
    }

    /** 상세 페이지 (공개) */
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        StudyCard s = new StudyCard(id, "데모 스터디 " + id, "모집중", "설명 텍스트", 7, fmt(LocalDate.now()));
        model.addAttribute("study", s);
        return "studies-detail"; // templates/studies-detail.html
    }

    /** 파일 페이지 (로그인 필요 가정) */
    @GetMapping("/{id}/files")
    public String files(@PathVariable("id") Long id, Model model) {
        model.addAttribute("studyId", id);
        model.addAttribute("files", List.of(
                new FileRow("ot.pdf", "공지/OT 자료"),
                new FileRow("week1.zip", "1주차 과제")
        ));
        return "studies-files"; // templates/studies-files.html
    }

    /** 참여 신청 (POST) */
    @PostMapping("/{id}/apply")
    public String apply(@PathVariable("id") Long id) {
        return "redirect:/studies/" + id;
    }

    private static String fmt(LocalDate date) {
        return DATE_FMT.format(date);
    }

    // === 내부용 DTO ===
    public record StudyCard(
            Long id,
            String title,
            String badge,
            String intro,
            int memberCount,
            String createdAtText // ← 포맷된 날짜 문자열
    ) {}

    public record FileRow(String name, String memo) {}
}
