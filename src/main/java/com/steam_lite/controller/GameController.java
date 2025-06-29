package com.steam_lite.controller;

import com.steam_lite.domain.game.Game;
import com.steam_lite.dto.game.GameRegisterRequest;
import com.steam_lite.dto.game.GameRegisterResponse;
import com.steam_lite.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor

public class GameController {

    private final GameService gameService;

    @PostMapping("/games")  // POST store/games
    public ResponseEntity<GameRegisterResponse> registerGame(@Valid @RequestBody GameRegisterRequest request) {
        GameRegisterResponse response = gameService.registerGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
