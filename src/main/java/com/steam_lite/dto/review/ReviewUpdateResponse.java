package com.steam_lite.dto.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateResponse {

    @NotNull(message = "평점을 입력해주세요")
    private Integer rating;

    @NotNull(message = "평가 내용을 입력해주세요")
    private String content;
}
