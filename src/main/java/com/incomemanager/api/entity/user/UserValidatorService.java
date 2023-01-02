package com.incomemanager.api.entity.user;

import com.incomemanager.api.dto.UserProfileUpdateDTO;

public interface UserValidatorService {

    User validateProfileUpdate(UserProfileUpdateDTO userProfileUpdateDTO);
}
