package com.somika.travelbooker.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somika.travelbooker.config.JwtConfig;
import com.somika.travelbooker.dto.request.AuthenticationRequestDto;
import com.somika.travelbooker.dto.response.AuthenticationResponseDto;
import com.somika.travelbooker.dto.response.RefreshTokenResponseDto;
import com.somika.travelbooker.mapper.AccountMapper;
import com.somika.travelbooker.model.Account;
import com.somika.travelbooker.repository.AccountRepository;
import com.somika.travelbooker.service.AuthenticationService;
import com.somika.travelbooker.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;

    @Override
    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );
        Account account = accountRepository.getAccountByEmail(authenticationRequest.email())
                .orElseThrow();

        String token = jwtUtil.issueToken(account.getEmail(), Map.of("role", account.getRole(), "id", account.getAccountId()));
        String refreshToken = jwtUtil.issueRefreshToken(account.getEmail());
        return new AuthenticationResponseDto(token, refreshToken, accountMapper.accountToAccountDto(account));
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(jwtConfig.getHeader());

        if (authHeader == null || !authHeader.startsWith(jwtConfig.getPrefix())) {
            return;
        }

        String refreshToken = authHeader.substring(7);
        String subject = jwtUtil.getSubject(refreshToken);

        if (subject != null) {
            Account account = accountRepository.getAccountByEmail(subject).orElseThrow();
            if (jwtUtil.isTokenValid(refreshToken, subject, account.getTokenRevokedLastAt())) {
                String accessToken = jwtUtil.issueToken(subject, Map.of("role", account.getRole().name()));

                RefreshTokenResponseDto responseBody = new RefreshTokenResponseDto(accessToken, refreshToken);

                try {
                    String jsonResponseBody = objectMapper.writeValueAsString(responseBody);
                    response.setContentType("application/json");
                    response.getWriter().write(jsonResponseBody);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
