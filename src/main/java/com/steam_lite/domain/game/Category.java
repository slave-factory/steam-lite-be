package com.steam_lite.domain.game;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game_categories") // ERD에 명시된 테이블 이름
@Getter
@Setter // Service 계층에서 필드 변경을 위해 필요. (일반적으로 지양되나, 간소화를 위해 사용)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private GameCategories name;

//    @ManyToMany(mappedBy = "categoryId")
//    private List<Game> games = new ArrayList<>();

    @ManyToMany(mappedBy = "categories")
    private List<Game> games = new ArrayList<>();
}