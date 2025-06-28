package com.steam_lite.domain.user;
import com.steam_lite.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

// 구매한 게임 목록(`purchased_games`)은 현재 구현 범위에서 제외하고, 추후 필요할 때 관계 설정 및 엔티티 추가
// import com.yourcompany.yourproject.domain.user_game.UserGame;
// import java.util.ArrayList;
// import java.util.List;

@Entity
@Table(name = "users") // ERD에 명시된 테이블 이름
@Getter
@Setter // Service 계층에서 필드 변경을 위해 필요. (일반적으로 지양되나, 간소화를 위해 사용)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // ERD에 명시된 컬럼명
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password; // 암호화된 비밀번호 저장

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role; // GUEST | USER | ADMIN

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status; // ONLINE | OFFLINE | AWAY

    // 'purchased_games' 등 다른 엔티티와의 관계는 현재 구현 범위에서 제외하고, 나중에 필요시 추가.
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Builder.Default
    // private List<UserGame> purchasedGames = new ArrayList<>();
}