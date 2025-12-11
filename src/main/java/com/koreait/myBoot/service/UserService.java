package com.koreait.myBoot.service;

import com.koreait.myBoot.dto.PasswordChangeRequest;
import com.koreait.myBoot.dto.ProfileUpdateRequest;
import com.koreait.myBoot.entity.User;
import com.koreait.myBoot.repository.StudyPostRepository;
import com.koreait.myBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudyPostRepository studyPostRepository;

    // --- 기존 메서드들 ---
    @Transactional
    public boolean registerUser(String username, String rawPassword, String name, String email) {
        if (userRepository.findByUsername(username).isPresent()) return false;

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .name(name)
                .email(email)
                .role("ROLE_USER")
                .emailVerified(false)
                .build();
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Transactional
    public void updateUserRole(Long id, String roleNoPrefix) {
        User u = userRepository.findById(id).orElseThrow();
        String role = roleNoPrefix.startsWith("ROLE_") ? roleNoPrefix : "ROLE_" + roleNoPrefix;
        u.setRole(role);
    }

    // --- 새로 추가 ---

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    /** 내 글 수 */
    public long countMyPosts(String username) {
        return studyPostRepository.countByAuthor(username);
    }

    /** 프로필 업데이트 */
    @Transactional
    public void updateProfile(String username, ProfileUpdateRequest req) {
        User me = userRepository.findByUsername(username).orElseThrow();

        // 닉네임 중복 검사(빈 값은 허용)
        if (req.getNickname() != null && !req.getNickname().isBlank()) {
            if (userRepository.existsByNicknameAndIdNot(req.getNickname(), me.getId())) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
            me.setNickname(req.getNickname().trim());
        } else {
            me.setNickname(null);
        }

        // 이메일 변경 시 간단 충돌검사(토큰 인증 미도입: TODO)
        if (userRepository.existsByEmailAndIdNot(req.getEmail(), me.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        me.setName(req.getName().trim());
        me.setEmail(req.getEmail().trim());
        me.setBio(req.getBio());
        me.setAvatarUrl(req.getAvatarUrl());
        // me.setEmailVerified(false); // 실제 인증 플로우를 붙일 때 사용
        // 저장은 더티체킹
    }

    /** 비밀번호 변경(현재 비번 검증 포함) */
    @Transactional
    public void changePassword(String username, PasswordChangeRequest req) {
        User me = userRepository.findByUsername(username).orElseThrow();

        if (!passwordEncoder.matches(req.getCurrentPassword(), me.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        if (passwordEncoder.matches(req.getNewPassword(), me.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 다른 비밀번호를 사용하세요.");
        }
        me.setPassword(passwordEncoder.encode(req.getNewPassword()));
        // 세션 무효화/재로그인은 보안상 권장이나, 여기선 서버 측 처리만(필요 시 SuccessHandler/Logout 유도)
    }
}
