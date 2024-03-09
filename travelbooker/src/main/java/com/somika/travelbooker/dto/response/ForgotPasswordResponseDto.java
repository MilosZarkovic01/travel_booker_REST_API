package com.somika.travelbooker.dto.response;

import java.util.UUID;

public record ForgotPasswordResponseDto(String email, UUID id) {
}
