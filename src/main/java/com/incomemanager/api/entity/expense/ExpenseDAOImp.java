package com.incomemanager.api.entity.expense;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExpenseDAOImp implements ExpenseDAO {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private JdbcTemplate      jdbcTemplate;

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.saveAndFlush(expense);
    }

    @Override
    public Optional<Expense> findByUuid(String uuid) {
        return expenseRepository.findByUuid(uuid);
    }

    @Override
    public void deleteOtherThenThese(List<Expense> expenses) {
        if (expenses == null || expenses.size() == 0) {
            return;
        }

        StringBuilder query = new StringBuilder();

        query.append("UPDATE expenses ");
        query.append("SET deleted = true ");
        query.append("WHERE id NOT IN ( ");
        query.append("");

        List<Integer> deleteIds = new ArrayList<>();

        StringJoiner params = new StringJoiner(",");

        for (Expense expense : expenses) {
            params.add(" ? ");
            deleteIds.add(expense.getId().intValue());
        }

        query.append(params.toString());

        query.append(" ) ");
        
        log.info("query={}", query);
        log.info("deleteIds={}", deleteIds);

        try {

            int updateCount = jdbcTemplate.update(query.toString(), deleteIds.toArray());

        } catch (Exception e) {
            log.warn("Exception, msg={}", e.getLocalizedMessage());
        }
    }
}
