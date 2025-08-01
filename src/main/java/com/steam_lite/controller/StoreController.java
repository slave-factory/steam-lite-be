package com.steam_lite.controller;

import com.steam_lite.domain.store.Category;
import com.steam_lite.dto.store.GameCreateRequest;
import com.steam_lite.dto.store.GameCreateResponse;
import com.steam_lite.dto.store.GameDetailResponse;
import com.steam_lite.dto.store.GameListResponse;
import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import com.steam_lite.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/game")
    public List<GameListResponse> getAllGames() {
        return storeService.getAllGames();
    }

    @GetMapping("/{gameId}")
    public GameDetailResponse getGameDetail(@PathVariable Long gameId) {
        return storeService.getGameDetail(gameId);
    }

    @GetMapping
    public List<GameListResponse> searchGames(@RequestParam(required = false) String name, @RequestParam(required = false) String category){
        Category searchCategory = null;
        if(category != null) {
            try{
                searchCategory = Category.valueOf(category.toUpperCase());
            }catch(IllegalArgumentException e){
                throw new CustomException(ErrorCode.INVALID_CATEGORY);
            }
        }
        return storeService.searchGames(name, searchCategory);
    }

    @PostMapping("/game")
    @PreAuthorize("hasRole('ADMIN')")
    public GameCreateResponse createGame(@RequestBody GameCreateRequest request) {
        return storeService.createGame(request);
    }
}
