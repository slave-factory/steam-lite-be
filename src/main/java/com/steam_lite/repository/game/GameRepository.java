package com.steam_lite.repository.game;

import com.steam_lite.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// 추가 구현 예정
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByTitle(String title);

}
