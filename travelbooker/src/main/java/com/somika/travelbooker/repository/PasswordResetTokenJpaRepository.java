package com.somika.travelbooker.repository;

import com.somika.travelbooker.repository.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetTokenEntity, UUID> {
    void deleteByExpirationDateTimeBefore(LocalDateTime currentDateTime);

    boolean existsByAccountAccountId(Long accountId);

}
