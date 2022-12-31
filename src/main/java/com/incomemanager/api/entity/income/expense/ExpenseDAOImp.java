package com.incomemanager.api.entity.income.expense;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExpenseDAOImp implements ExpenseDAO{

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.saveAndFlush(expense);
    }
}
