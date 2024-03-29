package com.somika.travelbooker.repository.impl;

import com.somika.travelbooker.mapper.AccountMapper;
import com.somika.travelbooker.model.Account;
import com.somika.travelbooker.repository.AccountJpaRepository;
import com.somika.travelbooker.repository.AccountRepository;
import com.somika.travelbooker.repository.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<Account> getAllAccounts() {
        return accountMapper.accountEntitiesToAccounts(accountJpaRepository.findAll());
    }

    @Override
    public Optional<Account> getAccountById(Long accountId) {
        Optional<AccountEntity> accountEntity = accountJpaRepository.findById(accountId);
        return accountEntity.map(accountMapper::accountEntityToAccount);
    }

    @Override
    public Account insertAccount(Account account) {
        AccountEntity savedAccountEntity = accountJpaRepository.save(accountMapper.accountToAccountEntity(account));
        return accountMapper.accountEntityToAccount(savedAccountEntity);
    }

    @Override
    public boolean existsAccountWithEmail(String email) {
        return accountJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsAccountWithId(Long accountId) {
        return accountJpaRepository.existsById(accountId);
    }

    @Override
    public void deleteAccountById(Long accountId) {
        accountJpaRepository.deleteById(accountId);
    }

    @Override
    public void updateAccount(Account account) {
        accountJpaRepository.save(accountMapper.accountToAccountEntity(account));
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        Optional<AccountEntity> accountEntity = accountJpaRepository.findByEmail(email);
        return accountEntity.map(accountMapper::accountEntityToAccount);
    }
}
