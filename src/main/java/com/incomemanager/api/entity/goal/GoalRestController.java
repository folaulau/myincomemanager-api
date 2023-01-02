package com.incomemanager.api.entity.goal;

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
import com.incomemanager.api.dto.UserDTO;
import com.incomemanager.api.dto.UserProfileUpdateDTO;
import com.incomemanager.api.entity.user.UserRestController;
import com.incomemanager.api.utils.ObjectUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Goals", description = "Goal Operations")
@Slf4j
@RestController
public class GoalRestController {
    
    @Autowired
    private GoalService goalService;
    
    @Operation(summary = "Update Goals", description = "update goals")
    @PutMapping(value = "/goals")
    public ResponseEntity<List<GoalDTO>> createUpdateGoals(@RequestParam String accountUuid,
            @RequestBody List<GoalCreateUpdateDTO> goals) {
        log.info("createUpdateGoals={}", ObjectUtils.toJson(goals));

        List<GoalDTO> goalDTOs = goalService.save(accountUuid, goals);

        return new ResponseEntity<>(goalDTOs, OK);
    }

}
