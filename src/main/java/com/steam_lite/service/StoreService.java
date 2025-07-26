package com.steam_lite.service;

import com.steam_lite.domain.store.Category;
import com.steam_lite.domain.store.Game;
import com.steam_lite.dto.store.GameCreateRequest;
import com.steam_lite.dto.store.GameCreateResponse;
import com.steam_lite.dto.store.GameDetailResponse;
import com.steam_lite.dto.store.GameListResponse;
import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import com.steam_lite.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    private final GameRepository gameRepository;

    // GET /api/store/game
    public List<GameListResponse> getAllGames() {
        return gameRepository.findAll().stream().map(GameListResponse::from).toList();
    }

    // GET /api/store/{game_id}
    public GameDetailResponse getGameDetail(Long gameId) {
        return gameRepository.findById(gameId)
                .map(GameDetailResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));
    }

    // GET /api/store?name=&category=
    public List<GameListResponse> searchGames(String name, Category category) {
        List<Game> results;
        if (name != null && !name.isBlank() && category != null) { // 제목 + 카테고리
            results = gameRepository.findByTitleContainingIgnoreCaseAndCategory(name, category);
        }else if(name != null && !name.isBlank()){ // 제목
            results = gameRepository.findByTitleContainingIgnoreCase(name);
        }else if(category != null){ // 카테고리
            results = gameRepository.findByCategory(category);
        }else{ // 아무것도 없다면
            results = gameRepository.findAll();
        }
        return results.stream().map(GameListResponse::from).toList();
    }

    // POST /api/store/game
    @Transactional
    public GameCreateResponse createGame(GameCreateRequest request) {
        Category category;

        try{
            category = Category.valueOf(request.getCategory().toUpperCase());
        }catch (IllegalArgumentException e){
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        }

        Game game = Game.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(category)
                .price(request.getPrice())
                .downloadUrl(request.getDownloadUrl())
                .thumbnailUrl(request.getThumbnailUrl())
                .build();

        Game savedGame = gameRepository.save(game);

        return GameCreateResponse.from(savedGame);
    }
}
