package com.incomemanager.api.entity.goal;

import java.util.Optional;
import java.util.Set;

public interface GoalDAO {

    Goal save(Goal goal);
    
    Optional<Goal> findByUuid(String uuid);
}