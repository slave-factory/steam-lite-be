package com.steam_lite.domain.game;

import com.steam_lite.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "games")  // DB ERD에 명시된 테이블 이름
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Game extends BaseTimeEntity {

    // GameId는 자동으로 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private GameCategory category;

    @Column(name = "price", nullable = false)
    private int price;

    // URL은 GameService에서 생성
    @Column(name = "download_url", columnDefinition = "TEXT", unique = true)
    private String downloadUrl;

    @Column(name = "thumbnail_url", columnDefinition = "TEXT", unique = true)
    private String thumbnailUrl;

    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted;
}
