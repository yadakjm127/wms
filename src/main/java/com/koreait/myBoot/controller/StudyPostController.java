package com.koreait.myBoot.controller;

import com.koreait.myBoot.dto.StudyPostCreateRequest;
import com.koreait.myBoot.dto.StudyPostDetailResponse;
import com.koreait.myBoot.dto.StudyPostListResponse;
import com.koreait.myBoot.dto.StudyPostUpdateRequest;
import com.koreait.myBoot.service.StudyPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class StudyPostController {

    private final StudyPostService postService;

    @GetMapping
    public String list(@RequestParam(required = false) String category,
                       @RequestParam(required = false) String q,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String sort,
                       Model model) {

        Page<StudyPostListResponse> posts = postService.list(category, q, page, size, sort);
        model.addAttribute("posts", posts);
        model.addAttribute("category", category);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        return "posts";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        StudyPostDetailResponse post = postService.getDetail(id);
        model.addAttribute("post", post);
        return "post-detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("post", new StudyPostCreateRequest());
        return "post-form";
    }

    @PostMapping
    public String create(@ModelAttribute("post") @Valid StudyPostCreateRequest req,
                         BindingResult binding,
                         Authentication auth) {
        if (binding.hasErrors()) {
            return "post-form";
        }
        String username = (auth != null ? auth.getName() : "anonymous");
        Long id = postService.create(req, username);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, Authentication auth) {
        String username = (auth != null ? auth.getName() : null);
        postService.ensureOwner(id, username); // 작성자만 접근
        StudyPostDetailResponse detail = postService.getDetail(id);

        StudyPostUpdateRequest form = new StudyPostUpdateRequest();
        form.setTitle(detail.getTitle());
        form.setContent(detail.getContent());

        model.addAttribute("id", id);
        model.addAttribute("post", form);
        return "post-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("post") @Valid StudyPostUpdateRequest req,
                         BindingResult binding,
                         Authentication auth) {
        if (binding.hasErrors()) {
            return "post-form";
        }
        String username = (auth != null ? auth.getName() : null);
        postService.update(id, req, username);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Authentication auth) {
        String username = (auth != null ? auth.getName() : null);
        postService.delete(id, username);
        return "redirect:/posts";
    }
}
