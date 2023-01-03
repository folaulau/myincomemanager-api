package com.incomemanager.api.entity.expense;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByUuid(String uuid);
}
