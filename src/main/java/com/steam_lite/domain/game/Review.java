package com.steam_lite.domain.game;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews") // ERD에 명시된 테이블 이름
@Getter
@Setter // Service 계층에서 필드 변경을 위해 필요. (일반적으로 지양되나, 간소화를 위해 사용)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // ERD에 명시된 컬럼명
    private Long id;

//    @Column(name = "game_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game gameId;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
