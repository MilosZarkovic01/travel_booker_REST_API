package com.somika.travelbooker.service.impl;

import com.somika.travelbooker.dto.AccountDto;
import com.somika.travelbooker.dto.request.AccountRegistrationRequestDto;
import com.somika.travelbooker.dto.request.ForgotPasswordRequestDto;
import com.somika.travelbooker.dto.request.PasswordUpdateRequestDto;
import com.somika.travelbooker.dto.request.ResetPasswordRequestDto;
import com.somika.travelbooker.dto.response.ForgotPasswordResponseDto;
import com.somika.travelbooker.exception.AccountIsAlreadyActivatedException;
import com.somika.travelbooker.exception.AccountNotFoundException;
import com.somika.travelbooker.exception.DuplicateResourceException;
import com.somika.travelbooker.mapper.AccountMapper;
import com.somika.travelbooker.model.Account;
import com.somika.travelbooker.repository.AccountRepository;
import com.somika.travelbooker.service.AccountService;
import com.somika.travelbooker.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetTokenService;

    @Override
    public AccountDto createAccount(AccountRegistrationRequestDto accountRegistrationRequest) {
        Account account = accountMapper.accountRegistrationRequestToAccount(accountRegistrationRequest);

        if (accountRepository.existsAccountWithEmail(account.getEmail())) {
            throw new DuplicateResourceException(
                    String.format("Email %s is already taken!", accountRegistrationRequest.email())
            );
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));

        Account savedAccount = accountRepository.insertAccount(account);

        return accountMapper.accountToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account with id %s not found", accountId)
                ));

        return accountMapper.accountToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountMapper.accountsToAccountDtos(accountRepository.getAllAccounts());
    }

    @Override
    public void deleteAccountById(Long accountId) {
        if (!accountRepository.existsAccountWithId(accountId)) {
            throw new AccountNotFoundException(String.format("Account with id %s not found", accountId));
        }

        accountRepository.deleteAccountById(accountId);
    }

    @Override
    public void updatePassword(PasswordUpdateRequestDto passwordUpdateRequestDto) {
        Account account = accountRepository.getAccountById(passwordUpdateRequestDto.accountId())
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account with id %s doesn't exist!", passwordUpdateRequestDto.accountId())
                ));

        if (!passwordEncoder.matches(passwordUpdateRequestDto.oldPassword(), account.getPassword())) {
            throw new BadCredentialsException("Old password is not matching!");
        }

        account.setPassword(passwordEncoder.encode(passwordUpdateRequestDto.newPassword()));
        account.setTokenRevokedLastAt(LocalDateTime.now());
        accountRepository.updateAccount(account);
    }

    @Override
    public void verifyEmail(Long accountId) {
        Account account = accountRepository.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account with id %s doesn't exist!", accountId)
                ));

        if (account.isActive()) {
            throw new AccountIsAlreadyActivatedException(String.format("Account with id %s is already activated", accountId));
        }

        account.setActive(true);
        accountRepository.updateAccount(account);
    }

    @Override
    public ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequest) {
        Account account = accountRepository.getAccountByEmail(forgotPasswordRequest.email())
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account with email %s doesn't exist!", forgotPasswordRequest.email())
                ));

        return passwordResetTokenService.createPasswordResetToken(account);
    }

    @Override
    public void resetPassword(UUID id, ResetPasswordRequestDto resetPasswordRequest) {
        if (!resetPasswordRequest.newPassword().equals(resetPasswordRequest.confirmedNewPassword())) {
            throw new BadCredentialsException("Passwords don't match");
        }

        Long accountId = passwordResetTokenService.validateToken(id);

        Account account = accountRepository.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account with id %s doesn't exist!", accountId)
                ));

        account.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        accountRepository.updateAccount(account);
    }
}
