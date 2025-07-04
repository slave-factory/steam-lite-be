package com.steam_lite.dto.game;

import com.steam_lite.domain.game.Category;
import com.steam_lite.domain.game.Game;
import com.steam_lite.domain.game.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResponse {
    private String gameId;
    private String title;
    private String description;
    private String category;
    private int price;
    private String thumbnailUrl;
    private int fileSize;
    private double ratingAvg;
    private int reviewCount;


    public static GameResponse from(Game game) {
        return GameResponse.builder()
                .gameId(game.getGamePK().toString())
                .title(game.getTitle())
                .description(game.getDescription() != null ? game.getDescription() : "")
                .category(game.getCategories().stream().map(Category::getName).toList().toString())
                .price(game.getPrice())
                .thumbnailUrl(game.getThumbnailUrl())
                .fileSize(0)
                .ratingAvg(game.getReviews().isEmpty() ? 0 : Math.round(game.getReviews().stream().mapToDouble(Review::getRating).sum()/game.getReviews().size()*100)/100.0)
                .reviewCount(game.getReviews().size())
                .build();

    }

    public static List<GameResponse> from(List<Game> games) {
        List<GameResponse> result = new ArrayList<>();
        for (Game game : games) {
           result.add(GameResponse.builder()
                   .gameId(game.getGamePK().toString())
                   .title(game.getTitle())
                   .description(game.getDescription() != null ? game.getDescription() : "")
                   .category(game.getCategories().stream().map(Category::getName).toList().toString())
                   .price(game.getPrice())
                   .thumbnailUrl(game.getThumbnailUrl())
                   .fileSize(0)
                   .ratingAvg(game.getReviews().isEmpty() ? 0 : Math.round(game.getReviews().stream().mapToDouble(Review::getRating).sum()/game.getReviews().size()*100)/100.0)
                   .reviewCount(game.getReviews().size())
                   .build());
        }
        return result;
    }
}
