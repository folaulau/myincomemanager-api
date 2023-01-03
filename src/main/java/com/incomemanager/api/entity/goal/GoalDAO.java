package com.incomemanager.api.entity.goal;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.incomemanager.api.entity.income.Income;

public interface GoalDAO {

    Goal save(Goal goal);
    
    Optional<Goal> findByUuid(String uuid);
    
    void deleteOtherThenThese(List<Goal> goals);
}