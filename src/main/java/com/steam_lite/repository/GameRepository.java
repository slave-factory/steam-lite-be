package com.steam_lite.repository;

import com.steam_lite.domain.store.Category;
import com.steam_lite.domain.store.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAll();
    Optional<Game> findById(Long id);
    List<Game> findByCategory(Category category);
    List<Game> findByTitleContainingIgnoreCase(String title);
    List<Game> findByTitleContainingIgnoreCaseAndCategory(String title, Category category);
}
