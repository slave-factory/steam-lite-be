package com.steam_lite.dto.game;

import com.steam_lite.domain.game.Category;
import com.steam_lite.domain.game.Game;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameUploadRequest {
    @NotBlank(message = "게임명은 필수 입력 항목입니다.")
    @Size(max = 50, message = "게임 설명은 50자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "게임 설명은 필수 입력 항목입니다.")
    @Size(min = 10, max = 50, message = "게임 설명은 10자 이상 5000자 이하로 입력해주세요.")
    private String description;

    @NotNull(message = "게임 가격은 필수 입력 항목입니다.")
    @PositiveOrZero(message = "게임 가격은 0과 같거나 커야 합니다.")
    private int price;

    @NotBlank(message = "게임 카테고리는 필수 입력 항목입니다.")
    private String categoryId;

    public Game toEntity(GameUploadRequest gameUploadRequest) {
        return Game.builder()
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .isDeleted(false)
                .build();
    }

}
