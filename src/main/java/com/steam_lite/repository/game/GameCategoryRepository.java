package com.steam_lite.repository.game;

import com.steam_lite.domain.game.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 추가 구현 예정
public interface GameCategoryRepository extends JpaRepository<GameCategory, Integer> {

    Optional<GameCategory> findByName(String name);
    Optional<GameCategory> findById(Integer id);
}
