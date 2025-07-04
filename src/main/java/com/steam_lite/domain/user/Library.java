package com.steam_lite.domain.user;

import com.steam_lite.domain.game.Game;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_library") // ERD에 명시된 테이블 이름
@Getter
@Setter // Service 계층에서 필드 변경을 위해 필요. (일반적으로 지양되나, 간소화를 위해 사용)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User libraryUserId;

//    @Column(name = "game_id", nullable = false)
//    @OneToOne(mappedBy = "gamePK")
//    private Game gameId;
}
