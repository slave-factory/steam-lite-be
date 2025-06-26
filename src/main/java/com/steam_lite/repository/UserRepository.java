package com.steam_lite.repository;

import com.steam_lite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    // GET /api/user/{user_id}에서 purchased_games는 현재 제외
    // Optional<User> findByIdWithPurchasedGames(@Param("id") Long id); // 이 메서드 제거
}