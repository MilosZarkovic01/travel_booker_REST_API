package com.somika.travelbooker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AuthenticationRequestDto(
        @Email(message = "Invalid email address")
        String email,
        @Size(min = 5, message = "Password must contain at least 5 characters")
        String password
) {
}
