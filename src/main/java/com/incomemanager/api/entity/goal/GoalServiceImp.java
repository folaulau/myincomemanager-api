package com.incomemanager.api.entity.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incomemanager.api.dto.EntityDTOMapper;
import com.incomemanager.api.dto.GoalCreateUpdateDTO;
import com.incomemanager.api.dto.GoalDTO;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.account.AccountDAO;
import com.incomemanager.api.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoalServiceImp implements GoalService {

    @Autowired
    private GoalDAO         goalDAO;

    @Autowired
    private AccountDAO      accountDAO;

    @Autowired
    private EntityDTOMapper entityDTOMapper;

    @Transactional
    @Override
    public List<GoalDTO> save(String accountUuid, List<GoalCreateUpdateDTO> goalCreateUpdateDTOs) {

        Account account = this.accountDAO.findByUuid(accountUuid).orElseThrow(() -> new ApiException("Account not found", "account not found for uuid=" + accountUuid));

        List<GoalDTO> goalDTOs = new ArrayList<>();

        for (GoalCreateUpdateDTO goalCreateUpdateDTO : goalCreateUpdateDTOs) {
            String uuid = goalCreateUpdateDTO.getUuid();
            Goal goal = null;
            if (uuid != null && uuid.isEmpty()) {

                Optional<Goal> optGoal = goalDAO.findByUuid(uuid);

                if (optGoal.isPresent()) {
                    goal = optGoal.get();
                    goal = entityDTOMapper.patchGoalWithGoalDTO(goalCreateUpdateDTO, goal);
                }
            }

            if (goal == null) {
                goal = entityDTOMapper.mapGoalCreateUpdateDTOToGoal(goalCreateUpdateDTO);
            }

            goal.setAccount(account);
            Goal savedGoal = this.goalDAO.save(goal);
            goalDTOs.add(this.entityDTOMapper.mapGoalToGoalDTO(savedGoal));
        }

        return goalDTOs;
    }

}
