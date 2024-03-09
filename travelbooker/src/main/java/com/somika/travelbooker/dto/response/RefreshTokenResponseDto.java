package com.somika.travelbooker.dto.response;

public record RefreshTokenResponseDto(
        String accessToken,
        String refreshToken
) {
}
