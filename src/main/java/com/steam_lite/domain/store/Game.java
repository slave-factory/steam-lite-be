package com.steam_lite.domain.store;

import com.steam_lite.domain.BaseTimeEntity;
import com.steam_lite.domain.review.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "rating_avg", nullable = false)
    private Double ratingAvg;

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    @Column(name = "download_url", nullable = false, unique = true)
    private String downloadUrl;

    @Column(name = "thumbnail_url", nullable = false, unique = true)
    private String thumbnailUrl;
}
