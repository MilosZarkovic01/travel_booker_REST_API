package com.somika.travelbooker.repository;

import com.somika.travelbooker.model.Role;
import com.somika.travelbooker.repository.entity.AccountEntity;
import com.somika.travelbooker.repository.entity.PasswordResetTokenEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PasswordResetTokenJpaRepositoryTest {

    @Autowired
    PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;

    @Autowired
    AccountJpaRepository accountJpaRepository;

    AccountEntity account;

    @BeforeEach
    void setUp() {
        account = AccountEntity.builder()
                .email("@test")
                .password("password")
                .active(true)
                .role(Role.USER)
                .build();

        accountJpaRepository.save(account);
    }

    @Test
    void deleteByExpirationDateTimeBefore() {
        UUID id = UUID.randomUUID();
        PasswordResetTokenEntity passwordResetTokenEntity =
                PasswordResetTokenEntity.builder()
                        .id(id)
                        .account(account)
                        .expirationDateTime(LocalDateTime.now().minusHours(1))
                        .build();

        passwordResetTokenJpaRepository.save(passwordResetTokenEntity);
        Assertions.assertThat(passwordResetTokenJpaRepository.findById(id).get()).isEqualTo(passwordResetTokenEntity);

        passwordResetTokenJpaRepository.deleteByExpirationDateTimeBefore(LocalDateTime.now());
        Assertions.assertThat(passwordResetTokenJpaRepository.findById(id)).isEqualTo(Optional.empty());
    }

    @Test
    void existsByAccountAccountId() {
        UUID id = UUID.randomUUID();
        PasswordResetTokenEntity passwordResetTokenEntity =
                PasswordResetTokenEntity.builder()
                        .id(id)
                        .account(account)
                        .expirationDateTime(LocalDateTime.now().minusHours(1))
                        .build();

        passwordResetTokenJpaRepository.save(passwordResetTokenEntity);
        Assertions.assertThat(passwordResetTokenJpaRepository.findById(id).get()).isEqualTo(passwordResetTokenEntity);

        assertThat(passwordResetTokenJpaRepository.existsByAccountAccountId(account.getAccountId())).isEqualTo(true);
    }
}