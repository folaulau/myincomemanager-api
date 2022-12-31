package com.incomemanager.api.entity.user;

import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.dto.AuthenticatorDTO;
import com.incomemanager.api.dto.UserSignUpDTO;

public interface UserService {

    AuthenticationResponseDTO signupWithEmailAndPassword(UserSignUpDTO userSignUpDTO);
}
