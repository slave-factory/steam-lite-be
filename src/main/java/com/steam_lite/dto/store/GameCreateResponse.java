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
public class GameCreateResponse {
    @JsonProperty("game_id")
    private String gameId;
    private String title;
    private String description;
    private Integer price;
    private String category;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("download_url")
    private String downloadUrl;

    public static GameCreateResponse from(Game game){
        return GameCreateResponse.builder()
                .gameId(String.valueOf(game.getId()))
                .title(game.getTitle())
                .description(game.getDescription())
                .price(game.getPrice())
                .category(game.getCategory().name())
                .thumbnailUrl(game.getThumbnailUrl())
                .downloadUrl(game.getDownloadUrl())
                .build();
    }
}
