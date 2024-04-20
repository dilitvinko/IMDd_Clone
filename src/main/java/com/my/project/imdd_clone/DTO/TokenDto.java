package com.my.project.imdd_clone.DTO;

import lombok.Builder;

public record TokenDto(
        String accessToken,
        UserDto user
) {
    @Builder public TokenDto {}
}
