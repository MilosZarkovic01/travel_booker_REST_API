package com.somika.travelbooker.dto.response;

import com.somika.travelbooker.dto.AccountDto;

public record AuthenticationResponseDto(
        String token,
        String refreshToken,
        AccountDto accountDto
) {
}
