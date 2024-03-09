package com.somika.travelbooker.service;

import com.somika.travelbooker.dto.response.ForgotPasswordResponseDto;
import com.somika.travelbooker.model.Account;

import java.util.UUID;

public interface PasswordResetTokenService {

    ForgotPasswordResponseDto createPasswordResetToken(Account account);

    Long validateToken(UUID id);

    void deleteExpiredPasswordResetTokens();
}
