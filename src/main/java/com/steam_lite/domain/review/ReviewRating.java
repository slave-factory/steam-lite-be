package com.steam_lite.domain.review;

import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import lombok.Getter;
import org.aspectj.lang.reflect.DeclareErrorOrWarning;

@Getter
public enum ReviewRating {

    OVERWHELMING_POSITIVE("압도적 긍정적", 0.6, 1.0),
    MOSTLY_POSITIVE("대체로 긍정적", 0.2, 0.6),
    MIXED("복합적", -0.2, 0.2),
    MOSTLY_NEGATIVE("대체로 부정적", -0.6, -0.2),
    OVERWHELMING_NEGATIVE("압도적 부정적", -1.0 ,-0.6);

    private final String name;
    private final double minRating;
    private final double maxRating;

    // (min <= x < max)의 규칙으로 작용
    ReviewRating(String name, double minRating, double maxRating) {
        this.name = name;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public static ReviewRating determineRating(double score) {
        for (ReviewRating rating : values()) {
            if (score >= rating.minRating && score <= rating.maxRating) {
                return rating;
            }
        }
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
