package com.somika.travelbooker.controller;

import com.somika.travelbooker.dto.AccountDto;
import com.somika.travelbooker.dto.request.AccountRegistrationRequestDto;
import com.somika.travelbooker.dto.request.ForgotPasswordRequestDto;
import com.somika.travelbooker.dto.request.PasswordUpdateRequestDto;
import com.somika.travelbooker.dto.request.ResetPasswordRequestDto;
import com.somika.travelbooker.dto.response.ForgotPasswordResponseDto;
import com.somika.travelbooker.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto registerAccount(
            @Valid @RequestBody AccountRegistrationRequestDto accountRegistrationRequestDto
    ) {
        final AccountDto accountDto = accountService.createAccount(accountRegistrationRequestDto);
        log.info("New account has been created {}", accountDto);

        return accountDto;
    }

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountId}")
    public AccountDto getAccountById(@PathVariable("accountId") Long accountId) {
        return accountService.getAccountById(accountId);
    }

    @PutMapping("/update-password")
    public void updatePassword(
            @Valid @RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto
    ) {
        accountService.updatePassword(passwordUpdateRequestDto);
        log.info("Password is updated");
    }

    @DeleteMapping(path = "/{accountId}")
    public void deleteAccountById(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccountById(accountId);
        log.info("Account with id {} is deleted", accountId);
    }


    @GetMapping("/verify-email/{accountId}")
    public void verifyEmail(@PathVariable("accountId") Long accountId) {
        accountService.verifyEmail(accountId);
    }


    @PostMapping("/forgot-password")
    public ForgotPasswordResponseDto forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequest) {
        return accountService.forgotPassword(forgotPasswordRequest);
    }

    @PutMapping("/reset-password/{resetToken}")
    public void resetPassword(@PathVariable("resetToken") UUID id, @Valid @RequestBody ResetPasswordRequestDto resetPasswordRequest) {
        accountService.resetPassword(id, resetPasswordRequest);
        log.info("Password is reset");
    }

}
