package com.incomemanager.api.entity.account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AccountDAOImp implements AccountDAO {
    
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public Optional<Account> findByUuid(String uuid) {
        return accountRepository.findByUuid(uuid);
    }

}
