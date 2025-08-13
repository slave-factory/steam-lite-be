package com.steam_lite.service;

import com.steam_lite.domain.store.Category;
import com.steam_lite.domain.store.Game;
import com.steam_lite.dto.store.*;
import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import com.steam_lite.repository.GameRepository;
import com.steam_lite.dto.s3.FileUploadResponse;
import com.steam_lite.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        FileUploadResponse thumbnailInfo = null;
        FileUploadResponse gameFileInfo = null;

        try{
            // 카테고리 확인
            category = Category.valueOf(request.getCategory().toUpperCase());

            // AWS에 thumbnail과 gameFile 저장
            thumbnailInfo = s3Service.uploadFile(thumbnail);
            gameFileInfo = s3Service.uploadFile(gameFile);

            // DB에 게임에 대한 메타 데이터 저장
            Game game = Game.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .category(category)
                    .price(request.getPrice())
                    .thumbnailKey(thumbnailInfo.getKey())
                    .downloadKey(gameFileInfo.getKey())
                    .thumbnailUrl(thumbnailInfo.getUrl())
                    .downloadUrl(gameFileInfo.getUrl())
                    .build();

            Game savedGame = gameRepository.save(game);

            return GameCreateResponse.from(savedGame);
        }catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        } catch (CustomException e) {
            if (thumbnailInfo != null) {
                s3Service.deleteFile(thumbnailInfo.getKey());
            }
            if (gameFileInfo != null) {
                s3Service.deleteFile(gameFileInfo.getKey());
            }
            throw e;
        }
    }

    // Key만 넘기는 방식으로 수정 가능
    // PUT /api/store/{gameId}
    @Transactional
    public void updateGame(Long gameId, GameUpdateRequest request, MultipartFile thumbnail, MultipartFile gameFile) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

        String newThumbnailKey = null;
        String newDownloadKey = null;

        String oldThumbnailKey = game.getThumbnailKey();
        String oldDownloadKey = game.getDownloadKey();

        try {
            if (thumbnail != null && !thumbnail.isEmpty()) {
                FileUploadResponse thumbnailInfo = s3Service.uploadFile(thumbnail);
                game.setThumbnailKey(thumbnailInfo.getKey());
                game.setThumbnailUrl(thumbnailInfo.getUrl());
                newThumbnailKey = thumbnailInfo.getKey();
            }

            if (gameFile != null && !gameFile.isEmpty()) {
                FileUploadResponse gameFileInfo = s3Service.uploadFile(gameFile);
                game.setDownloadKey(gameFileInfo.getKey());
                game.setDownloadUrl(gameFileInfo.getUrl());
                newDownloadKey = gameFileInfo.getKey();
            }

            if (request != null) {
                if (request.getTitle() != null) {
                    game.setTitle(request.getTitle());
                }
                if (request.getDescription() != null) {
                    game.setDescription(request.getDescription());
                }
                if (request.getPrice() != null) {
                    game.setPrice(request.getPrice());
                }
            }

        } catch (Exception e) {
            if (newThumbnailKey != null) {
                s3Service.deleteFile(newThumbnailKey);
            }
            if (newDownloadKey != null) {
                s3Service.deleteFile(newDownloadKey);
            }
            throw e;
        }

        // 이전 파일 삭제
        if (newThumbnailKey != null && oldThumbnailKey != null) {
            s3Service.deleteFile(oldThumbnailKey);
        }
        if (newDownloadKey != null && oldDownloadKey != null) {
            s3Service.deleteFile(oldDownloadKey);
        }
    }


    // DELETE /api/store/{gameId}
    @Transactional
    public void deleteGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

        String thumbnailKey = game.getThumbnailKey();
        String downloadKey = game.getDownloadKey();

        if ((thumbnailKey == null) || (thumbnailKey.isBlank()) || (downloadKey == null) || (downloadKey.isBlank())) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        s3Service.deleteFile(game.getThumbnailKey());
        s3Service.deleteFile(game.getDownloadKey());
        gameRepository.delete(game);
    }
}
