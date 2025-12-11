package com.koreait.myBoot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfileUpdateRequest {

    @NotBlank(message = "이름은 비워둘 수 없습니다.")
    @Size(max = 100, message = "이름은 100자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 비워둘 수 없습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 100)
    private String email;

    @Size(max = 40, message = "닉네임은 40자 이하여야 합니다.")
    private String nickname;              // 선택 사항

    @Size(max = 500, message = "소개는 500자 이하여야 합니다.")
    private String bio;                    // 선택 사항

    @Size(max = 255)
    private String avatarUrl;              // 파일 업로드 미도입: 경로 직접 저장(선택)
}
