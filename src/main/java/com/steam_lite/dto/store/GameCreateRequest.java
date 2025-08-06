package com.steam_lite.dto.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameCreateRequest {

    @NotBlank(message = "게임 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "게임 설명을 입력해주세요.")
    private String description;

    @NotNull(message = "게임 가격을 입력해주세요.")
    private Integer price;

    @NotBlank(message = "게임 카테고리를 입력해주세요.")
    private String category;
}
