package com.steam_lite.service;

import com.steam_lite.domain.store.Category;
import com.steam_lite.domain.store.Game;
import com.steam_lite.dto.store.*;
import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import com.steam_lite.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    private final GameRepository gameRepository;
    private final S3Service s3Service;

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
    public GameCreateResponse createGame(GameCreateRequest request, MultipartFile thumbnail, MultipartFile gameFile) {
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
                .thumbnailUrl(s3Service.uploadFile(thumbnail))
                .downloadUrl(s3Service.uploadFile(gameFile))
                .build();

        Game savedGame = gameRepository.save(game);

        return GameCreateResponse.from(savedGame);
    }

    // PUT /api/store/{gameId}
    @Transactional
    public void updateGame(Long gameId, GameUpdateRequest request, MultipartFile thumbnail, MultipartFile gameFile) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

        // AWS에서 삭제하고 DB에 새로운 URL 생성
        if (thumbnail != null && !thumbnail.isEmpty()) {
            s3Service.deleteFile(extractKeyFromUrl(game.getThumbnailUrl()));
            game.setThumbnailUrl(s3Service.uploadFile(thumbnail));
        }

        if (gameFile != null && !gameFile.isEmpty()) {
            s3Service.deleteFile(extractKeyFromUrl(game.getDownloadUrl()));
            game.setDownloadUrl(s3Service.uploadFile(gameFile));
        }

        if (request != null) {
            game.setTitle(request.getTitle());
            game.setDescription(request.getDescription());
            game.setPrice(request.getPrice());
        }
    }

    //DELETE /api/store/{gameId}
    @Transactional
    public void deleteGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

        s3Service.deleteFile(extractKeyFromUrl(game.getDownloadUrl()));
        s3Service.deleteFile(extractKeyFromUrl(game.getThumbnailUrl()));
        gameRepository.delete(game);
    }

    // url로부터 key를 얻기 위한 함수
    // 예외 부분 수정 필요함
    private String extractKeyFromUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            return path;
        } catch (URISyntaxException e) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
