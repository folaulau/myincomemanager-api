package com.incomemanager.api.entity.expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incomemanager.api.dto.EntityDTOMapper;
import com.incomemanager.api.dto.ExpenseCreateUpdateDTO;
import com.incomemanager.api.dto.ExpenseDTO;
import com.incomemanager.api.dto.IncomeCreateUpdateDTO;
import com.incomemanager.api.dto.IncomeDTO;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.account.AccountDAO;
import com.incomemanager.api.entity.income.Income;
import com.incomemanager.api.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExpenseServiceImp implements ExpenseService {
    
    @Autowired
    private ExpenseDAO expenseDAO;
    

    @Autowired
    private AccountDAO      accountDAO;

    @Autowired
    private EntityDTOMapper entityDTOMapper;

    @Override
    public List<ExpenseDTO> save(String accountUuid, List<ExpenseCreateUpdateDTO> expenseCreateUpdateDTOs) {
        Account account = this.accountDAO.findByUuid(accountUuid).orElseThrow(() -> new ApiException("Account not found", "account not found for uuid=" + accountUuid));

        List<Expense> expenses = new ArrayList<>();

        for (ExpenseCreateUpdateDTO expenseCreateUpdateDTO : expenseCreateUpdateDTOs) {
            String uuid = expenseCreateUpdateDTO.getUuid();
            Expense expense = null;
            if (uuid != null && !uuid.isEmpty()) {

                Optional<Expense> optIncome = expenseDAO.findByUuid(uuid);

                if (optIncome.isPresent()) {
                    expense = optIncome.get();
                    expense = entityDTOMapper.patchExpenseWithIncomeCreateUpdateDTO(expenseCreateUpdateDTO, expense);
                }
            }

            if (expense == null) {
                expense = entityDTOMapper.mapIncomeCreateUpdateDTOToExpense(expenseCreateUpdateDTO);
            }

            expense.setAccount(account);
            Expense savedExpense = this.expenseDAO.save(expense);
            expenses.add(savedExpense);
        }
        
        expenseDAO.deleteOtherThenThese(expenses);
        
        List<ExpenseDTO> expenseDTOs = entityDTOMapper.mapExpensesToExpenseDTOs(expenses);

        return expenseDTOs;
    }

}
