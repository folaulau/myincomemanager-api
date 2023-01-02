package com.incomemanager.api.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.incomemanager.api.entity.address.Address;
import com.incomemanager.api.entity.goal.Goal;
import com.incomemanager.api.entity.income.Income;
import com.incomemanager.api.entity.user.User;

// @formatter:off
@Mapper(componentModel = "spring", 
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, 
unmappedTargetPolicy = ReportingPolicy.IGNORE,  
nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
//@formatter:on
public interface EntityDTOMapper {

    UserDTO mapUserToUserDTO(User user);

    AuthenticationResponseDTO mapUserToAuthenticationResponse(User user);

    @Mappings({@Mapping(target = "uuid", ignore = true),@Mapping(target = "account", ignore = true)})
    User patchUserWithUserProfileUpdateDTO(UserProfileUpdateDTO dto, @MappingTarget User user);

    @Mappings({@Mapping(target = "uuid", ignore = true)})
    Address patchAddressWithAddressDTO(AddressDTO addressDTO, @MappingTarget Address address);

    GoalDTO mapGoalToGoalDTO(Goal goal);

    @Mappings({@Mapping(target = "uuid", ignore = true)})
    Goal patchGoalWithGoalDTO(GoalCreateUpdateDTO goalCreateUpdateDTO, @MappingTarget Goal goal);

    List<Goal> mapGoalCreateUpdateDTOsToGoals(List<GoalCreateUpdateDTO> goalCreateUpdateDTOs);

    Goal mapGoalCreateUpdateDTOToGoal(GoalCreateUpdateDTO goalCreateUpdateDTO);

    Income patchIncomeWithIncomeDTO(IncomeCreateUpdateDTO incomeCreateUpdateDTO, @MappingTarget Income income);

    Income mapIncomeCreateUpdateDTOToIncome(IncomeCreateUpdateDTO incomeCreateUpdateDTO);

    IncomeDTO mapIncomeToIncomeDTO(Income savedIncome);
}
