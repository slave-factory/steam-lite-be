package com.steam_lite.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileUpdateRequest {

    @Size(min=2, max=50, message = "사용자 이름은 2자 이상 50자 이하로 입력해주세요.")
    private String username;

    @Email(message = "유효한 이메일 주소 형식이 아닙니다.")
    private String email;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

}
