package com.incomemanager.api.entity.income;

import java.util.Optional;

public interface IncomeDAO {

    Income save(Income income);
    
    Optional<Income> findByUuid(String uuid);
}
