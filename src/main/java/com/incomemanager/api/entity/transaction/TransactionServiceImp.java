package com.incomemanager.api.entity.transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incomemanager.api.dto.EntityDTOMapper;
import com.incomemanager.api.dto.TransactionCreateDTO;
import com.incomemanager.api.dto.TransactionDTO;
import com.incomemanager.api.dto.TransactionUpdateDTO;
import com.incomemanager.api.entity.user.User;
import com.incomemanager.api.entity.user.UserDAO;
import com.incomemanager.api.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImp implements TransactionService {

    @Autowired
    private TransactionDAO  transactionDAO;

    @Autowired
    private UserDAO         userDAO;

    @Autowired
    private EntityDTOMapper entityDTOMapper;

    @Transactional
    @Override
    public List<TransactionDTO> save(List<TransactionCreateDTO> newTransactions) {

        List<Transaction> transactions = new ArrayList<>();

        for (TransactionCreateDTO transactionCreateDTO : newTransactions) {

            String userUuid = transactionCreateDTO.getUserUuid();
            User user = userDAO.findByUuid(userUuid).orElseThrow(() -> new ApiException("User not found", "user not found for uuid=" + userUuid));

            Transaction transaction = entityDTOMapper.mapTransactionCreateDTOToTransaction(transactionCreateDTO);
            transaction.setUser(user);
            transaction.generateTotal();

            if (transaction.getDatetime() == null) {
                transaction.setDatetime(LocalDateTime.now());
            }

            transaction = transactionDAO.save(transaction);

            transactions.add(transaction);
        }
        return entityDTOMapper.mapTransactionsToTransactionDTOs(transactions);

    }

    @Override
    public TransactionDTO update(TransactionUpdateDTO updateTransaction) {

        String userUuid = updateTransaction.getUserUuid();
        User user = userDAO.findByUuid(userUuid).orElseThrow(() -> new ApiException("User not found", "user not found for uuid=" + userUuid));

        String uuid = updateTransaction.getUuid();

        Transaction transaction = transactionDAO.findByUuid(uuid).orElseThrow(() -> new ApiException("Transaction not found", "transaction not found by uuid=" + uuid));

        transaction = entityDTOMapper.patchTransactionWithTransactionUpdateDTO(updateTransaction, transaction);
        transaction.generateTotal();
        transaction.setUser(user);
        
        transaction = transactionDAO.save(transaction);

        return entityDTOMapper.mapTransactionToTransactionDTO(transaction);
    }

}
