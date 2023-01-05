package com.incomemanager.api.entity.transaction;

import java.util.List;

import com.incomemanager.api.dto.TransactionCreateDTO;
import com.incomemanager.api.dto.TransactionDTO;
import com.incomemanager.api.dto.TransactionUpdateDTO;

public interface TransactionService {

    List<TransactionDTO> save(List<TransactionCreateDTO> newTransactions);

    TransactionDTO update(TransactionUpdateDTO updateTransaction);

}
