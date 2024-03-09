package com.somika.travelbooker.repository;

import com.somika.travelbooker.model.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository {

    void createPasswordResetToken(PasswordResetToken passwordResetToken);

    Optional<PasswordResetToken> getPasswordResetTokenById(UUID id);

    void deleteExpiredPasswordResetTokens(LocalDateTime now);

    boolean isPasswordResetTokenAlreadySentToAccount(Long accountId);
}
