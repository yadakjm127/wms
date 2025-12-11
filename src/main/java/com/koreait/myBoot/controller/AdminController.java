// src/main/java/com/koreait/myBoot/controller/AdminController.java
package com.koreait.myBoot.controller;

import com.koreait.myBoot.entity.User;
import com.koreait.myBoot.service.AdminService;
import com.koreait.myBoot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    /** /admin -> 관리자 홈(대시보드) */
    @GetMapping
    public String adminRoot(Model model) {
        model.addAttribute("pageTitle", "관리자 홈");
        model.addAttribute("totalUsers", adminService.getTotalUsers());
        model.addAttribute("adminCount", adminService.getAdminCount());
        model.addAttribute("userCount", adminService.getUserCount());

        // 최근 가입자 8명 (id 내림차순)
        List<User> recentUsers = userService.getAllUsers().stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .limit(8)
                .toList();
        model.addAttribute("recentUsers", recentUsers);

        return "admin-home"; // templates/admin-home.html
    }

    /** 사용자 관리 */
    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "사용자 관리");
        return "admin-users"; // templates/admin-users.html (s 주의!)
    }

    /** 폼 버튼 호환 */
    @PostMapping("/users/{id}/make-admin")
    public String makeAdmin(@PathVariable Long id) {
        userService.updateUserRole(id, "ADMIN");
        return "redirect:/admin/users";
    }

    /** 폼 버튼 호환 */
    @PostMapping("/users/{id}/make-user")
    public String makeUser(@PathVariable Long id) {
        userService.updateUserRole(id, "USER");
        return "redirect:/admin/users";
    }

    /** JS fetch로 권한 변경 */
    @PostMapping("/users/{id}/role")
    @ResponseBody
    public String changeRole(@PathVariable Long id, @RequestParam("role") String roleNoPrefix) {
        if (!"ADMIN".equalsIgnoreCase(roleNoPrefix) && !"USER".equalsIgnoreCase(roleNoPrefix)) {
            return "invalid role";
        }
        userService.updateUserRole(id, roleNoPrefix);
        return "ok";
    }
}
