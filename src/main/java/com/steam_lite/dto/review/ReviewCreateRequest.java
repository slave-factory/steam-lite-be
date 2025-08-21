package com.steam_lite.dto.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    @NotBlank(message = "평점을 입력해주세요")
    private Integer rating;

    @NotBlank(message = "평가 내용을 입력해주세요")
    private String content;
}
