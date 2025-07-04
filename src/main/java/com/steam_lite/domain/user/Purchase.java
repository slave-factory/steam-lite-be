package com.steam_lite.domain.user;

import com.steam_lite.domain.game.Game;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchases") // ERD에 명시된 테이블 이름
@Getter
@Setter // Service 계층에서 필드 변경을 위해 필요. (일반적으로 지양되나, 간소화를 위해 사용)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // ERD에 명시된 컬럼명
    private Long id;

//    @Column(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

//    @Column(name = "game_id", nullable = false)
//    @OneToOne(mappedBy = "gamePK")
//    private Game gameId;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "currency", nullable = false, length = 5)
    private String currency;

    @Column(name = "provider", length = 50)
    private String provider;

    @CreatedDate
    @Column(name = "paid_at", updatable = false)
    private LocalDateTime paidAt;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;
}
