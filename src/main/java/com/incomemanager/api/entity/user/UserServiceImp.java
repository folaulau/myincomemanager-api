package com.incomemanager.api.entity.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.dto.AuthenticatorDTO;
import com.incomemanager.api.dto.UserSignUpDTO;
import com.incomemanager.api.jwt.JwtTokenService;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public AuthenticationResponseDTO signupWithEmailAndPassword(UserSignUpDTO userSignUpDTO) {
        // TODO Auto-generated method stub
        return null;
    }

}
