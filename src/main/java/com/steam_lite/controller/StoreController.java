package com.steam_lite.controller;

import com.steam_lite.dto.game.GameResponse;
import com.steam_lite.dto.game.StoreResponse;
import com.steam_lite.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/store")
    public ResponseEntity<List<StoreResponse>> getStore() {
        return ResponseEntity.ok(this.storeService.store());
    }

    @GetMapping("/store/{gameId}")
    public ResponseEntity<GameResponse> getGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(this.storeService.getGame(gameId));
    }

    @GetMapping("/store")
    public ResponseEntity<List<GameResponse>> getGames(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        return ResponseEntity.ok(this.storeService.getGames(name, category));
    }

//    @PostMapping("/store/test")
}
