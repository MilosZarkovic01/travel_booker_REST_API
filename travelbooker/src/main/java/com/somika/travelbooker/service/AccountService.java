package com.somika.travelbooker.service;

import com.somika.travelbooker.dto.AccountDto;
import com.somika.travelbooker.dto.request.AccountRegistrationRequestDto;
import com.somika.travelbooker.dto.request.ForgotPasswordRequestDto;
import com.somika.travelbooker.dto.request.ResetPasswordRequestDto;
import com.somika.travelbooker.dto.request.PasswordUpdateRequestDto;
import com.somika.travelbooker.dto.response.ForgotPasswordResponseDto;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDto createAccount(AccountRegistrationRequestDto accountRegistrationRequest);

    AccountDto getAccountById(Long accountId);

    List<AccountDto> getAllAccounts();

    void deleteAccountById(Long accountId);

    void updatePassword(PasswordUpdateRequestDto passwordUpdateRequestDto);

    void verifyEmail(Long accountId);

    ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequest);

    void resetPassword(UUID id, ResetPasswordRequestDto resetPasswordRequest);
}
