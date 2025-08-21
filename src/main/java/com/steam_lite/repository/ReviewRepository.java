package com.steam_lite.repository;

import com.steam_lite.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserIdAndGameId(String userId, Long gameId);
}
