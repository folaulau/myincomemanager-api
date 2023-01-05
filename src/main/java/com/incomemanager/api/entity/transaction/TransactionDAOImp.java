package com.incomemanager.api.entity.transaction;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionDAOImp implements TransactionDAO {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public Optional<Transaction> findByUuid(String uuid) {
        return transactionRepository.findByUuid(uuid);
    }

}
