package com.somika.travelbooker.service.impl;

import com.somika.travelbooker.model.Account;
import com.somika.travelbooker.model.AccountUserDetails;
import com.somika.travelbooker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public AccountUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Account: %s not found", username)));

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + account.getRole().name()));

        return new AccountUserDetails(account.getEmail(), account.getPassword(), account.isActive(), true, true, true, authorities, account.getTokenRevokedLastAt());
    }
}
