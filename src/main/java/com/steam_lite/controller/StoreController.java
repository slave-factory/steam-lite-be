package com.steam_lite.controller;

import com.steam_lite.dto.game.GameResponse;
import com.steam_lite.dto.game.GameUploadRequest;
import com.steam_lite.dto.game.GameUploadResponse;
import com.steam_lite.dto.game.StoreResponse;
import com.steam_lite.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/store/game")
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

    @PostMapping("/store/purchase/{gameId}")
    public String purchaseGame(@PathVariable Long gameId) {
        /** TODO: 나중에 세션 구현 후 개발 */
        return "redirect:/";
    }

    @PostMapping("/store/games")
    public ResponseEntity<GameUploadResponse> gameUpload(@Valid @RequestBody GameUploadRequest request) {
        GameUploadResponse response = this.storeService.getGameUpload(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
