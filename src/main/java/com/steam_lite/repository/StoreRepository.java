package com.steam_lite.repository;

import com.steam_lite.domain.game.Category;
import com.steam_lite.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByTitleAndCategories(String title, Category category);
    Optional<Game> findByTitle(String title);
    Optional<Game> findByCategories(Category category);

}
