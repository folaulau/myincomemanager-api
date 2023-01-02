package com.incomemanager.api.entity.goal;

import java.util.List;
import java.util.Set;

import com.incomemanager.api.dto.GoalCreateUpdateDTO;
import com.incomemanager.api.dto.GoalDTO;

public interface GoalService {

    List<GoalDTO> save(String accountUuid, List<GoalCreateUpdateDTO> goals);
}
