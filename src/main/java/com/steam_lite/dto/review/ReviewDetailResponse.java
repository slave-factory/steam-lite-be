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
public class ReviewDetailResponse {
    @JsonProperty("review_id")
    private String reviewId;

    @JsonProperty("user_id")
    private String userId;

    private String username;

    private Integer rating;

    private String content;;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static ReviewDetailResponse from(Review review) {
        return ReviewDetailResponse.builder()
                .reviewId(String.valueOf(review.getId()))
                .userId(String.valueOf(review.getUserId()))
                .username(review.getUsername())
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
