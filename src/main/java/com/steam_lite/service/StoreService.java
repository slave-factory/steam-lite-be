package com.steam_lite.service;

import com.steam_lite.domain.game.Category;
import com.steam_lite.domain.game.Game;
import com.steam_lite.domain.game.GameCategories;
import com.steam_lite.dto.game.GameResponse;
import com.steam_lite.dto.game.StoreResponse;
import com.steam_lite.repository.CategoryRepository;
import com.steam_lite.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        System.out.println(category+" sldkfjlskdfjk");
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
}
