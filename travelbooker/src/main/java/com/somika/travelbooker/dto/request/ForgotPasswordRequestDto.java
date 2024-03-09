package com.somika.travelbooker.dto.request;

import jakarta.validation.constraints.Email;

public record ForgotPasswordRequestDto(
        @Email(message = "Invalid email address")
        String email
) {
}
