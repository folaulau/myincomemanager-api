package com.incomemanager.api.entity.income;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class IncomeDAOImp implements IncomeDAO {

    @Autowired
    private JdbcTemplate     jdbcTemplate;

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public Income save(Income income) {
        // TODO Auto-generated method stub
        return incomeRepository.saveAndFlush(income);
    }

    @Override
    public Optional<Income> findByUuid(String uuid) {
        // TODO Auto-generated method stub
        return incomeRepository.findByUuid(uuid);
    }

    @Override
    public void deleteOtherThenThese(List<Income> incomes) {

        if (incomes == null || incomes.size() == 0) {
            return;
        }

        StringBuilder query = new StringBuilder();

        query.append("UPDATE incomes ");
        query.append("SET deleted = true ");
        query.append("WHERE id NOT IN ( ");
        query.append("");

        List<Integer> deleteIds = new ArrayList<>();
        
        StringJoiner params = new StringJoiner(",");
        
        for (Income income : incomes) {
            params.add(" ? ");
            deleteIds.add(income.getId().intValue());
        }
        
        query.append(params.toString());

        query.append(" ) ");

        try {

            int updateCount = jdbcTemplate.update(query.toString(), deleteIds.toArray());

        } catch (Exception e) {
            log.warn("Exception, msg={}", e.getLocalizedMessage());
        }
    }
}
