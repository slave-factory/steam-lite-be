package com.steam_lite.controller;

import com.steam_lite.domain.store.Category;
import com.steam_lite.dto.store.*;
import com.steam_lite.service.S3Service;
import com.steam_lite.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final S3Service s3Service;

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
    public GameCreateResponse createGame(
            @RequestPart(value = "data") GameCreateRequest request,
            @RequestPart(value = "thumbnail") MultipartFile thumbnail,
            @RequestPart(value = "game") MultipartFile gameFile
    ) {
        return storeService.createGame(request, thumbnail, gameFile);
    }

    // 사용자 인증 부분 구현 필요
    @PutMapping("/{gameId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateGame(
            @PathVariable Long gameId,
            @RequestPart(value = "data", required = false) GameUpdateRequest request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart(value = "game", required = false) MultipartFile gameFile) {
        storeService.updateGame(gameId, request, thumbnail, gameFile);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{gameId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        storeService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }

}
