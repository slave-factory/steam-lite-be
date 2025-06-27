package com.steam_lite.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.steam_lite.domain.user.User; // User 엔티티 임포트
import com.steam_lite.domain.user.UserRole;
import com.steam_lite.domain.user.UserStatus; // UserStatus 임포트
import lombok.*;

import java.time.LocalDateTime;
// purchased_games 관련 import 제거
// import java.util.List;
// import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String userId; // API 명세에 string 타입으로 되어 있어 Long -> String 변환
    private String username;
    private String email;
    private String profileImageUrl;
    private UserRole role;
    private UserStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    // purchased_games 필드 제거
    // private List<UserGameResponse> purchasedGames;

    // User 엔티티를 UserResponse DTO로 변환하는 정적 팩토리 메서드
    public static UserResponse from(User user) {
        // purchased_games 변환 로직 제거
        // List<UserGameResponse> purchasedGames = user.getPurchasedGames().stream()
        //         .map(UserGameResponse::from)
        //         .collect(Collectors.toList());

        return UserResponse.builder()
                .userId(String.valueOf(user.getId())) // Long -> String 변환
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                // .purchasedGames(purchasedGames) // 필드 제거
                .build();
    }
}