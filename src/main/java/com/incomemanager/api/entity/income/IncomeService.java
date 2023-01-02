package com.incomemanager.api.entity.income;

import java.util.List;

import com.incomemanager.api.dto.IncomeCreateUpdateDTO;
import com.incomemanager.api.dto.IncomeDTO;

public interface IncomeService {

    List<IncomeDTO> save(String accountUuid, List<IncomeCreateUpdateDTO> incomeCreateUpdateDTOs);
}
