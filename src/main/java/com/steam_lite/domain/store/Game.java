package com.steam_lite.domain.store;

import com.steam_lite.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Game extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "download_url", nullable = false, unique = true)
    private String downloadUrl;

    @Column(name = "thumbnail_url", nullable = false, unique = true)
    private String thumbnailUrl;

    @Builder.Default // 별도로 설정하지 않는다면 기본값은 false
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
