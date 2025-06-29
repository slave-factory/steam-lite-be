package com.steam_lite.service;

import com.steam_lite.domain.game.Game;
import com.steam_lite.domain.game.GameCategory;
import com.steam_lite.dto.game.GameRegisterRequest;
import com.steam_lite.dto.game.GameRegisterResponse;
import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import com.steam_lite.repository.game.GameCategoryRepository;
import com.steam_lite.repository.game.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameCategoryRepository gameCategoryRepository;

    // POST /store/games
    public GameRegisterResponse registerGame(GameRegisterRequest request) {

        // 카테고리를 넣기 위해 카테고리 먼저 탐색
        GameCategory category = gameCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // 카테고리를 보내며, 요청
        Game game = request.toEntity(category);
        Game savedGame = gameRepository.save(game); // 게임을 save 하여 gameId를 먼저 생성

        // URL 생성
        String downloadUrl = generateDownloadUrl(savedGame);
        String thumbnailUrl = generateThumbnailUrl(savedGame);

        savedGame.setDownloadUrl(downloadUrl);
        savedGame.setThumbnailUrl(thumbnailUrl);
        gameRepository.save(savedGame);

        return GameRegisterResponse.from(savedGame);

    }

    // API 명세서에 따라 DownloadURL 생성
    private String generateDownloadUrl(Game game) {
        return "https://steam-lite.com/store/download/" + String.valueOf(game.getId());   // Integer -> String 명시적으로 표현
    }

    // API 명세서에 따라 ThumbnailURL 생성
    private String generateThumbnailUrl(Game game) {
        return "https://steam-lite.com/store/thumbnail/" + String.valueOf(game.getId()) + ".jpg"; // Integer -> String 명시적으로 표현
    }


}
