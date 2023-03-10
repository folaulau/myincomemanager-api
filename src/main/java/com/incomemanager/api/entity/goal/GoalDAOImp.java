package com.incomemanager.api.entity.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.incomemanager.api.entity.income.Income;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class GoalDAOImp implements GoalDAO {

    @Autowired
    private JdbcTemplate   jdbcTemplate;

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal save(Goal goal) {
        return goalRepository.saveAndFlush(goal);
    }

    @Override
    public Optional<Goal> findByUuid(String uuid) {
        return goalRepository.findByUuid(uuid);
    }

    @Override
    public void deleteOtherThenThese(List<Goal> goals) {
        if (goals == null || goals.size() == 0) {
            return;
        }

        StringBuilder query = new StringBuilder();

        query.append("UPDATE goals ");
        query.append("SET deleted = true ");
        query.append("WHERE id NOT IN ( ");
        query.append("");

        List<Integer> deleteIds = new ArrayList<>();

        StringJoiner params = new StringJoiner(",");

        for (Goal goal : goals) {
            params.add(" ? ");
            deleteIds.add(goal.getId().intValue());
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
