package com.incomemanager.api.entity.user;

import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.dto.AuthenticatorDTO;
import com.incomemanager.api.dto.UserDTO;
import com.incomemanager.api.dto.UserProfileUpdateDTO;

public interface UserService {

    AuthenticationResponseDTO authenticate(AuthenticatorDTO authenticatorDTO);

    UserDTO updateProfile(UserProfileUpdateDTO userProfileUpdateDTO);
}
