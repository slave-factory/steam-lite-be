package com.steam_lite.dto.game;

import com.steam_lite.domain.game.Category;
import com.steam_lite.domain.game.Game;
import com.steam_lite.domain.game.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {
    private String gameId;
    private String title;
    private String category;
    private int price;
    private String thumbnailUrl;

    public static StoreResponse from(Game game) {
        return StoreResponse.builder()
                .gameId(game.getGamePK().toString())
                .title(game.getTitle())
                .category(game.getCategories().stream().map(Category::getName).toList().toString())
                .price(game.getPrice())
                .thumbnailUrl(game.getThumbnailUrl())
                .build();
    }
}
