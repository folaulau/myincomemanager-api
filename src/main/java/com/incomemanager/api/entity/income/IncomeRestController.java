package com.incomemanager.api.entity.income;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incomemanager.api.dto.GoalCreateUpdateDTO;
import com.incomemanager.api.dto.GoalDTO;
import com.incomemanager.api.dto.IncomeCreateUpdateDTO;
import com.incomemanager.api.dto.IncomeDTO;
import com.incomemanager.api.entity.goal.GoalRestController;
import com.incomemanager.api.entity.goal.GoalService;
import com.incomemanager.api.utils.ObjectUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Income", description = "Income Operations")
@Slf4j
@RestController
public class IncomeRestController {

    @Autowired
    private IncomeService incomeService;
    
    @Operation(summary = "Update Incomes", description = "update incomes")
    @PutMapping(value = "/incomes")
    public ResponseEntity<List<IncomeDTO>> createUpdateIncomes(@RequestParam String accountUuid,
            @RequestBody List<IncomeCreateUpdateDTO> incomes) {
        log.info("createUpdateIncomes={}", ObjectUtils.toJson(incomes));

        List<IncomeDTO> goalDTOs = incomeService.save(accountUuid, incomes);

        return new ResponseEntity<>(goalDTOs, OK);
    }
}
