package com.steam_lite.domain.game;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "game_categories")    //DB ERD에 명시된 테이블명
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GameCategory {

    // category_id는 미리 DB에 저장해두기
    @Id
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}
