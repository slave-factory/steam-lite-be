package com.steam_lite.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {
    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
