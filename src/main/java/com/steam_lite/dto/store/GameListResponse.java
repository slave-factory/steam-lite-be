package com.steam_lite.dto.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.steam_lite.domain.store.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameListResponse {

    @JsonProperty("game_id")
    private String gameId;
    private String title;
    private String category;
    private Integer price;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    public static GameListResponse from(Game game){
        return GameListResponse.builder()
                .gameId(String.valueOf(game.getId()))
                .title(game.getTitle())
                .category(game.getCategory().name())
                .price(game.getPrice())
                .thumbnailUrl(game.getThumbnailUrl())
                .build();
    }
}
