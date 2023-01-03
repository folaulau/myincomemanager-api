package com.incomemanager.api.entity.income;

import java.util.List;
import java.util.Optional;

public interface IncomeDAO {

    Income save(Income income);
    
    Optional<Income> findByUuid(String uuid);

    void deleteOtherThenThese(List<Income> incomes);
}
