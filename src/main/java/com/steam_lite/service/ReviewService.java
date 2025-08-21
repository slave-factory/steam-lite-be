package com.steam_lite.service;


import com.steam_lite.domain.review.Review;
import com.steam_lite.domain.store.Game;
import com.steam_lite.domain.user.User;
import com.steam_lite.dto.review.*;
import com.steam_lite.repository.GameRepository;
import com.steam_lite.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.steam_lite.exception.*;
import com.steam_lite.security.CustomUserDetails;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;

    // POST /api/store/{gameId}/review
    public ReviewCreateResponse createReview(Long gameId, ReviewCreateRequest request, User user) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

        Optional<Review> existingReview = reviewRepository.findByUserIdAndGameId(String.valueOf(user.getId()), gameId);
        if (existingReview.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_REVIEW);
        }

        // 리뷰를 작성할 때마다, 게임의 리뷰 개수와 리뷰 평균 평점 계산
        game.setRatingAvg((((game.getRatingAvg() * game.getReviewCount()) + request.getRating())) / (game.getReviewCount() + 1));
        game.setReviewCount(game.getReviewCount() + 1);

        Review review = Review.builder()
                .game(game)
                .rating(request.getRating())
                .content(request.getContent())
                .userId(String.valueOf(user.getId()))
                .username(user.getUsername())
                .isDeleted(false)
                .build();

        Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.from(savedReview);
    }

    // GET /api/store/{gameId}/review
    public List<ReviewListResponse> getAllReviews(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));
        return game.getReviews().stream().map(ReviewListResponse::from).toList();
    }

    // GET /api/store/{gameId}/review/{reviewId}
    public ReviewDetailResponse getReviewDetail(Long gameId, Long reviewId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        return ReviewDetailResponse.from(review);
    }

    // PUT /api/store/{gameId}/review/{reviewId}
    public void updateReview(Long gameId, Long reviewId, ReviewUpdateResponse request, User user) {

        Game game = gameRepository.findById(gameId).orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (String.valueOf(user.getId()).compareTo(review.getUserId()) != 0) {
            throw new CustomException(ErrorCode.HANDLE_ACCESS_DENIED);
        }
        
        if (request.getRating() == null || request.getContent() == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        // 점수 수정이 있을 경우, 평점 평균 다시 계산하기
        if (!review.getRating().equals(request.getRating())) {
            // 남은 리뷰의 수의 수에 따라 다르게 처리
            if (game.getReviewCount() == 1) {
                game.setReviewCount(1);
                game.setRatingAvg(game.getRatingAvg() * (-1.0));
            }
            else {
                // 해당 리뷰의 점수를 아예 삭제한 뒤에 다시 추가
                game.setRatingAvg(((game.getRatingAvg() * game.getReviewCount()) - review.getRating()) / (game.getReviewCount() - 1));
                game.setReviewCount(game.getReviewCount() - 1);
                game.setRatingAvg((((game.getRatingAvg() * game.getReviewCount()) + request.getRating())) / (game.getReviewCount() + 1));
                game.setReviewCount(game.getReviewCount() + 1);
            }
        }
        
        review.setRating(request.getRating());
        review.setContent(request.getContent());
    }

    // DELETE /api/store/{gameId}/review/{reviewId}
    public void deleteReview(Long gameId, Long reviewId, User user) {

        Game game = gameRepository.findById(gameId).orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (String.valueOf(user.getId()).compareTo(review.getUserId()) != 0) {
            throw new CustomException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        // 남은 리뷰의 수의 수에 따라 다르게 처리
        if (game.getReviewCount() == 1) {
            game.setReviewCount(0);
            game.setRatingAvg(0.0);
        }
        else {
            // 리뷰를 삭제할 경우 평점을 다시 계산
            game.setRatingAvg(((game.getRatingAvg() * game.getReviewCount()) - review.getRating()) / (game.getReviewCount() - 1));
            game.setReviewCount(game.getReviewCount() - 1);
        }

        // 지금은 그냥 바로 삭제하는 코드
        reviewRepository.deleteById(reviewId);
    }
}
