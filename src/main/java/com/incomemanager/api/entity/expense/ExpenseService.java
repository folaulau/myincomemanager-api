package com.incomemanager.api.entity.expense;

import java.util.List;

import com.incomemanager.api.dto.ExpenseCreateUpdateDTO;
import com.incomemanager.api.dto.ExpenseDTO;

public interface ExpenseService {

    List<ExpenseDTO> save(String accountUuid, List<ExpenseCreateUpdateDTO> expenses);
}
