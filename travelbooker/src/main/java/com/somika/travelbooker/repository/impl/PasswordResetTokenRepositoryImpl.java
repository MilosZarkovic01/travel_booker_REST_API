package com.somika.travelbooker.repository.impl;

import com.somika.travelbooker.mapper.PasswordResetTokenMapper;
import com.somika.travelbooker.model.PasswordResetToken;
import com.somika.travelbooker.repository.PasswordResetTokenJpaRepository;
import com.somika.travelbooker.repository.PasswordResetTokenRepository;
import com.somika.travelbooker.repository.entity.PasswordResetTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PasswordResetTokenRepositoryImpl implements PasswordResetTokenRepository {

    private final PasswordResetTokenJpaRepository passwordResetTokenRepository;
    private final PasswordResetTokenMapper passwordResetTokenMapper;

    @Override
    public void createPasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetTokenMapper.modelToEntity(passwordResetToken));
    }

    @Override
    public Optional<PasswordResetToken> getPasswordResetTokenById(UUID id) {
        Optional<PasswordResetTokenEntity> passwordResetTokenEntity = passwordResetTokenRepository.findById(id);
        return passwordResetTokenEntity.map(passwordResetTokenMapper::entityToModel);
    }

    @Override
    public void deleteExpiredPasswordResetTokens(LocalDateTime now) {
        passwordResetTokenRepository.deleteByExpirationDateTimeBefore(now);
    }

    @Override
    public boolean isPasswordResetTokenAlreadySentToAccount(Long accountId) {
        return passwordResetTokenRepository.existsByAccountAccountId(accountId);
    }
}
