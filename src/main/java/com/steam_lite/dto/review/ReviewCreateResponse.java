package com.steam_lite.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.steam_lite.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateResponse {

    @JsonProperty("review_id")
    private String reviewId;

    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("user_id")
    private String userId;

    private Integer rating;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static ReviewCreateResponse from(Review review) {
        return ReviewCreateResponse.builder()
                .reviewId(String.valueOf(review.getId()))
                .gameId(String.valueOf(review.getGame().getId()))
                .userId(review.getUserId())
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
