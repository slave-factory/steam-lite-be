package com.steam_lite.dto.game;

import com.steam_lite.domain.game.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameUploadResponse {
    private String gameId;
    private String message;
    private String downloadUrl;
    private String thumbnailUrl;

    public static GameUploadResponse from(Game game) {
        return GameUploadResponse.builder()
                .gameId(game.getGamePK().toString())
                .message("정상적으로 등록되었습니다")
                .downloadUrl("https://localhost:8080/download"+game.getGamePK().toString())
                .thumbnailUrl("https://localhost:8080/thumbnail/"+game.getGamePK().toString())
                .build();
    }
}
