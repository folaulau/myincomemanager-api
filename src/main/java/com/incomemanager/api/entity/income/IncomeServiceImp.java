package com.incomemanager.api.entity.income;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incomemanager.api.dto.EntityDTOMapper;
import com.incomemanager.api.dto.GoalCreateUpdateDTO;
import com.incomemanager.api.dto.GoalDTO;
import com.incomemanager.api.dto.IncomeCreateUpdateDTO;
import com.incomemanager.api.dto.IncomeDTO;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.account.AccountDAO;
import com.incomemanager.api.entity.goal.Goal;
import com.incomemanager.api.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IncomeServiceImp implements IncomeService {

    @Autowired
    private IncomeDAO       incomeDAO;

    @Autowired
    private AccountDAO      accountDAO;

    @Autowired
    private EntityDTOMapper entityDTOMapper;

    @Override
    public List<IncomeDTO> save(String accountUuid, List<IncomeCreateUpdateDTO> incomeCreateUpdateDTOs) {
        Account account = this.accountDAO.findByUuid(accountUuid).orElseThrow(() -> new ApiException("Account not found", "account not found for uuid=" + accountUuid));

        List<IncomeDTO> incomeDTOs = new ArrayList<>();

        for (IncomeCreateUpdateDTO incomeCreateUpdateDTO : incomeCreateUpdateDTOs) {
            String uuid = incomeCreateUpdateDTO.getUuid();
            Income income = null;
            if (uuid != null && uuid.isEmpty()) {

                Optional<Income> optIncome = incomeDAO.findByUuid(uuid);

                if (optIncome.isPresent()) {
                    income = optIncome.get();
                    income = entityDTOMapper.patchIncomeWithIncomeDTO(incomeCreateUpdateDTO, income);
                }
            }

            if (income == null) {
                income = entityDTOMapper.mapIncomeCreateUpdateDTOToIncome(incomeCreateUpdateDTO);
            }

            income.setAccount(account);
            Income savedIncome = this.incomeDAO.save(income);
            incomeDTOs.add(this.entityDTOMapper.mapIncomeToIncomeDTO(savedIncome));
        }

        return incomeDTOs;
    }

}
