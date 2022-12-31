package com.incomemanager.api.dto;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.incomemanager.api.entity.user.User;

// @formatter:off
@Mapper(componentModel = "spring", 
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, 
unmappedTargetPolicy = ReportingPolicy.IGNORE,  
nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
//@formatter:on
public interface EntityDTOMapper {

    UserDTO mapUserToUserDTO(User user);
}
