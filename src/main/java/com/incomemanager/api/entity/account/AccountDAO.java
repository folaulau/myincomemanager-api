package com.incomemanager.api.entity.account;

import java.util.Optional;

public interface AccountDAO {

    Account save(Account account);

    Optional<Account> findByUuid(String uuid);
}
