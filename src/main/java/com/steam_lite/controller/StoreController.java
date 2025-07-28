package com.steam_lite.controller;

import com.steam_lite.domain.store.Category;
import com.steam_lite.dto.store.*;
import com.steam_lite.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<GameListResponse> searchGames(@RequestParam(required = false) String name, @RequestParam(required = false) Category category){
        return storeService.searchGames(name, category);
    }

    @PostMapping("/game")
    @PreAuthorize("hasRole('ADMIN')")
    public GameCreateResponse createGame(@RequestBody GameCreateRequest request) {
        return storeService.createGame(request);
    }

    @PutMapping("/{gameId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateGame(@PathVariable Long gameId, @RequestBody GameUpdateRequest request) {
        storeService.updateGame(gameId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{gameId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        storeService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }

}
