package com.incomemanager.api.entity.expense;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incomemanager.api.dto.ExpenseCreateUpdateDTO;
import com.incomemanager.api.dto.ExpenseDTO;
import com.incomemanager.api.utils.ObjectUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Expenses", description = "Expense Operations")
@Slf4j
@RestController
public class ExpenseRestController {
    
    @Autowired
    private ExpenseService expenseService;

    @Operation(summary = "Update Expenses", description = "update expenses")
    @PutMapping(value = "/expenses")
    public ResponseEntity<List<ExpenseDTO>> createUpdateExpenses(@RequestParam String accountUuid,
            @RequestBody List<ExpenseCreateUpdateDTO> expenses) {
        log.info("createUpdateExpenses={}", ObjectUtils.toJson(expenses));

        List<ExpenseDTO> expenseDTOs = expenseService.save(accountUuid, expenses);

        return new ResponseEntity<>(expenseDTOs, OK);
    }
}
