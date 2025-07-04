package com.steam_lite.domain.game;

import com.steam_lite.domain.user.UserRole;
import com.steam_lite.domain.user.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games") // ERD에 명시된 테이블 이름
@Getter
@Setter // Service 계층에서 필드 변경을 위해 필요. (일반적으로 지양되나, 간소화를 위해 사용)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // ERD에 명시된 컬럼명
    private Long gamePK;

    @OneToMany(mappedBy = "gameId")
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

//    @Column(name = "category_id", nullable = false)
//    @ManyToMany(mappedBy = "games")
//    private List<Category> categoryId;

    @ManyToMany
    @JoinTable(
            name = "game_category_map", // 생성될 중간 테이블 이름
            joinColumns = @JoinColumn(name = "game_id"), // Game 테이블을 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "category_id") // Category 테이블을 참조하는 외래키
    )
    private List<Category> categories = new ArrayList<>();

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "download_url", unique = true, columnDefinition = "TEXT")
    private String downloadUrl;

    @Column(name = "thumbnail_url", unique = true, columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    // 'purchased_games' 등 다른 엔티티와의 관계는 현재 구현 범위에서 제외하고, 나중에 필요시 추가.
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Builder.Default
    // private List<UserGame> purchasedGames = new ArrayList<>();
}
