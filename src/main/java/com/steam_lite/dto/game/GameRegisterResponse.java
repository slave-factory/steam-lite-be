package com.steam_lite.dto.game;


import com.steam_lite.domain.game.Game;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GameRegisterResponse {

    private String gameId;
    private String message;
    private String downloadUrl;
    private String thumbnailUrl;

    public static GameRegisterResponse from(Game game) {
        return GameRegisterResponse.builder()
                .gameId(String.valueOf(game.getId()))
                .message("게임 등록에 성공했습니다.")
                .downloadUrl(game.getDownloadUrl())
                .thumbnailUrl(game.getThumbnailUrl())
                .build();
    }
}
