package com.steam_lite.dto.game;


import com.steam_lite.domain.game.Game;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameUpdateRequest {

    private String title;

    private String description;

    @PositiveOrZero(message = "게임의 가격은 0과 같거나 커야 합니다.")
    private Integer price;
}
