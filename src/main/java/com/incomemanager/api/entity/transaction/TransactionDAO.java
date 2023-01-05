package com.incomemanager.api.entity.transaction;

import java.util.Optional;

public interface TransactionDAO {

    Transaction save(Transaction transaction);

    Optional<Transaction> findByUuid(String uuid);
}
