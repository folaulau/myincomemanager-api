package com.incomemanager.api.entity.goal;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class GoalDAOImp implements GoalDAO {
    
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

}
