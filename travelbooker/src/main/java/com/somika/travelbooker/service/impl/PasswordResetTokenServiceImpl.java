package com.somika.travelbooker.service.impl;

import com.somika.travelbooker.dto.response.ForgotPasswordResponseDto;
import com.somika.travelbooker.exception.DuplicateResourceException;
import com.somika.travelbooker.exception.PasswordResetTokenExpiredException;
import com.somika.travelbooker.exception.PasswordResetTokenNotFoundException;
import com.somika.travelbooker.model.Account;
import com.somika.travelbooker.model.PasswordResetToken;
import com.somika.travelbooker.repository.PasswordResetTokenRepository;
import com.somika.travelbooker.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${password-reset-token.expiration-time}")
    private Long expirationTime;

    @Override
    public ForgotPasswordResponseDto createPasswordResetToken(Account account) {
        if (passwordResetTokenRepository.isPasswordResetTokenAlreadySentToAccount(account.getAccountId())) {
            throw new DuplicateResourceException(
                    String.format("Password reset token is already sent to email %s", account.getEmail())
            );
        }
        PasswordResetToken passwordResetToken =
                PasswordResetToken.builder()
                        .id(UUID.randomUUID())
                        .account(account)
                        .expirationDateTime(LocalDateTime.now().plusMinutes(expirationTime))
                        .build();

        passwordResetTokenRepository.createPasswordResetToken(passwordResetToken);

        return new ForgotPasswordResponseDto(passwordResetToken.getAccount().getEmail(), passwordResetToken.getId());
    }

    @Override
    public Long validateToken(UUID id) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.getPasswordResetTokenById(id)
                .orElseThrow(() -> new PasswordResetTokenNotFoundException(
                        String.format("Password reset token with id %s not found", id)
                ));

        if (passwordResetToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
            throw new PasswordResetTokenExpiredException("Password reset token has expired!");
        }

        return passwordResetToken.getAccount().getAccountId();
    }


    @Override
    @Transactional
    @Scheduled(fixedRateString = "${scheduler.fixed-rate}", timeUnit = TimeUnit.HOURS)
    public void deleteExpiredPasswordResetTokens() {
        passwordResetTokenRepository.deleteExpiredPasswordResetTokens(LocalDateTime.now());
    }
}
