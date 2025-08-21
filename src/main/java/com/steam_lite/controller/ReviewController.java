package com.steam_lite.controller;

import com.steam_lite.domain.user.User;
import com.steam_lite.dto.review.*;
import com.steam_lite.security.CustomUserDetails;
import com.steam_lite.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{gameId}/review")
    public ReviewCreateResponse createReview(
            @PathVariable Long gameId,
            @RequestBody ReviewCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return reviewService.createReview(gameId, request, user);
    }

    @GetMapping("/{gameId}/review")
    public List<ReviewListResponse> getAllReviews(@PathVariable Long gameId) {
        return reviewService.getAllReviews(gameId);
    }

    @GetMapping("/{gameId}/review/{reviewId}")
    public ReviewDetailResponse getReviewDetail(@PathVariable Long gameId, @PathVariable Long reviewId) {
        return reviewService.getReviewDetail(gameId, reviewId);
    }

    @PutMapping("/{gameId}/review/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @PathVariable Long gameId,
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateResponse request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        reviewService.updateReview(gameId, reviewId, request, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{gameId}/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long gameId ,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        reviewService.deleteReview(gameId, reviewId, user);
        return ResponseEntity.noContent().build();
    };

}
