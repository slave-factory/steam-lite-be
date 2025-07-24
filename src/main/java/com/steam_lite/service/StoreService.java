package com.steam_lite.service;

import com.steam_lite.domain.game.Category;
import com.steam_lite.domain.game.Game;
import com.steam_lite.domain.game.GameCategories;
import com.steam_lite.dto.game.*;
import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import com.steam_lite.repository.CategoryRepository;
import com.steam_lite.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<StoreResponse> store() {
        return this.storeRepository.findAll().stream().map(StoreResponse::from).toList();
    }

    @Transactional
    public GameResponse getGame(Long gameId) {
        return GameResponse.from(this.storeRepository.findById(gameId).orElseThrow());
    }

    @Transactional
    public List<GameResponse> getGames(String name, String category) {
        Category category1;
        if(category != null && !category.isBlank()){
            category1=this.categoryRepository.findByName(GameCategories.valueOf(category)).get();
        }else {
            category1=null;
        }
        if(name != null && !name.isBlank() && category1 != null){
            return this.storeRepository.findByTitleAndCategories(name, category1).stream().map(GameResponse::from).toList();
        }else if(name != null && !name.isBlank() && category1 == null){
            return this.storeRepository.findByTitle(name).stream().map(GameResponse::from).toList();
        }else if((name == null || name.isBlank()) && category1 != null) {
            return GameResponse.from(this.storeRepository.findByCategories(category1));
        }else {
            return this.storeRepository.findAll().stream().map(GameResponse::from).toList();
        }
    }

    @Transactional
    public GameUploadResponse getGameUpload(GameUploadRequest request) {
        Game game = request.toEntity(request);
        Game savedGame = storeRepository.save(game);
        savedGame.setDownloadUrl("https://localhost:8080/download/"+game.getGamePK().toString());
        savedGame.setThumbnailUrl("https://localhost:8080/thumbnail/"+game.getGamePK().toString());
        String[] arr=request.getCategoryId().split(",");

        List<Category> categories = new ArrayList<>();
        for(String categoryId : arr) {
            Optional<Category> category = this.categoryRepository.findById(Long.parseLong(categoryId));
            if(category.isPresent()) {
                categories.add(category.get());
            }
        }

        savedGame.setCategories(categories);
        savedGame = storeRepository.save(savedGame);
        return GameUploadResponse.from(savedGame);
    }

    @Transactional
    public void updateGame(Long gameId, GameUpdateRequest request) {
        Optional<Game> selectedGame = this.storeRepository.findById(gameId);

        // 수정하려는 게임 존재 여부 확인
        if (selectedGame.isPresent()) {
            if (request.getTitle() != null) {
                selectedGame.get().setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                selectedGame.get().setDescription(request.getDescription());
            }
            if (request.getPrice() != null) {
                selectedGame.get().setPrice(request.getPrice());
            }
        }
        else {
            throw new CustomException(ErrorCode.GAME_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteGame(Long gameId) {
        Optional<Game> selectedGame = this.storeRepository.findById(gameId);

        if (selectedGame.isPresent()) {
            storeRepository.delete(selectedGame.get());
        }
        else {
            throw new CustomException(ErrorCode.GAME_NOT_FOUND);
        }
    }
}
