package com.incomemanager.api.entity.transaction;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incomemanager.api.dto.IncomeCreateUpdateDTO;
import com.incomemanager.api.dto.IncomeDTO;
import com.incomemanager.api.dto.TransactionCreateDTO;
import com.incomemanager.api.dto.TransactionDTO;
import com.incomemanager.api.dto.TransactionUpdateDTO;
import com.incomemanager.api.entity.income.IncomeRestController;
import com.incomemanager.api.utils.ObjectUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Transactions", description = "Transaction Operations")
@Slf4j
@RestController
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Create Transactions", description = "create transactions")
    @PostMapping(value = "/transactions")
    public ResponseEntity<List<TransactionDTO>> createTransactions(@RequestBody List<TransactionCreateDTO> newTransactions) {
        log.info("createTransactions={}", ObjectUtils.toJson(newTransactions));

        List<TransactionDTO> transactionDTOs = transactionService.save(newTransactions);

        return new ResponseEntity<>(transactionDTOs, OK);
    }

    @Operation(summary = "Update Transaction", description = "update transaction")
    @PutMapping(value = "/transactions")
    public ResponseEntity<TransactionDTO> updateTransaction(@RequestBody TransactionUpdateDTO updateTransaction) {
        log.info("updateTransaction={}", ObjectUtils.toJson(updateTransaction));

        TransactionDTO transactionDTO = transactionService.update(updateTransaction);

        return new ResponseEntity<>(transactionDTO, OK);
    }
}
