package com.incomemanager.api.entity.income;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    Optional<Income> findByUuid(String uuid);
}
