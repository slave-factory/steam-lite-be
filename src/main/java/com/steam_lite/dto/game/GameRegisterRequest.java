package com.steam_lite.dto.game;

import com.steam_lite.domain.game.Game;
import com.steam_lite.domain.game.GameCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.*;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GameRegisterRequest {

    @NotBlank(message = "게임 이름을 입력해주세요.")
    @Size(min = 1, max = 50, message = "게임 이름은 1자 이상 50자 이하로 해주세요.")
    private String title;

    @Size(max = 500, message =  "게임 설명은 500자를 초과할 수 없습니다.")
    private String description;

    @NotBlank(message = "게임의 가격을 입력해주세요.")
    private int price;

    @NotBlank(message = "게임 카테고리 아이디를 입력해주세요")
    private Integer categoryId;

    // Thumbnail과 Download URL은 응답 부분에서 구현
    // GameService에서 카테고리를 넘겨 받음
    public Game toEntity(GameCategory category) {
        return  Game.builder()
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .category(category)
                .build();
    }

}
