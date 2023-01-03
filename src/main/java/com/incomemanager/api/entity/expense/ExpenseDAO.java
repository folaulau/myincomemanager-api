package com.incomemanager.api.entity.expense;

import java.util.List;
import java.util.Optional;

import com.incomemanager.api.entity.goal.Goal;

public interface ExpenseDAO {

    Expense save(Expense expense);

    Optional<Expense> findByUuid(String uuid);
    
    void deleteOtherThenThese(List<Expense> expenses);
}
