package com.steam_lite.dto.user;

import com.steam_lite.domain.user.User; // User 엔티티 임포트
import com.steam_lite.domain.user.UserStatus; // UserStatus 임포트
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpRequest {
    @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
    @Size(min = 2, max = 50, message = "사용자 이름은 2자 이상 50자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 주소 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자를 초과할 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;

    // Request DTO를 User 엔티티로 변환하는 메서드
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .password(passwordEncoder.encode(this.password)) // 비밀번호 암호화
                .status(UserStatus.OFFLINE) // 초기 상태 OFFLINE
                .profileImageUrl(null) // 초기 프로필 이미지 없음
                .build();
    }
}