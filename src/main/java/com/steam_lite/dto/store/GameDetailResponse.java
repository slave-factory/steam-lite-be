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
public class GameDetailResponse {
    @JsonProperty("game_id")
    private String gameId;
    private String title;
    private String description;
    private String category;
    private Integer price;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("download_url")
    private String downloadUrl;

    @JsonProperty("file_size")
    private Integer fileSize;

    @JsonProperty("rating_avg")
    private Double ratingAvg;

    @JsonProperty("review_count")
    private Integer reviewCount;

    public static GameDetailResponse from(Game game){
        return GameDetailResponse.builder()
                .gameId(String.valueOf(game.getId()))
                .title(game.getTitle())
                .description(game.getDescription())
                .category(game.getCategory().name())
                .price(game.getPrice())
                .thumbnailUrl(game.getThumbnailUrl())
                .downloadUrl(game.getDownloadUrl())
                .fileSize(0)
                .ratingAvg(0.0)
                .reviewCount(0)
                .build();
    }
}
