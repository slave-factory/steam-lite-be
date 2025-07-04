package com.steam_lite.repository;

import com.steam_lite.domain.game.Category;
import com.steam_lite.domain.game.GameCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(GameCategories name);
}
