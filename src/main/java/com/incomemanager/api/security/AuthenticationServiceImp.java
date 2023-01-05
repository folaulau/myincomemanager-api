package com.incomemanager.api.security;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.dto.EntityDTOMapper;
import com.incomemanager.api.entity.user.User;
import com.incomemanager.api.jwt.JwtPayload;
import com.incomemanager.api.jwt.JwtTokenService;
import com.incomemanager.api.utils.ObjectUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EntityDTOMapper     entityDTOMapper;

    @Autowired
    private JwtTokenService     jwtTokenService;

    @Override
    public AuthenticationResponseDTO authenticate(User user) {
        String jwt = jwtTokenService.generateToken(user);
        AuthenticationResponseDTO auth = entityDTOMapper.mapUserToAuthenticationResponse(user);
        auth.setToken(jwt);
        auth.setRole(user.getRoleAsString());
        auth.setAccountUuid(user.getAccount().getUuid());
        auth.setAccountStatus(user.getAccount().getStatus());
        auth.setSignUpStatus(user.getAccount().getSignUpStatus());
        return auth;
    }

    @Override
    public boolean authorizeRequest(String token, JwtPayload jwtPayload) {

        log.debug("jwtPayload={}", ObjectUtils.toJson(jwtPayload));

        // ApiSessionUtils.setSessionToken( jwtPayload);

        return true;
    }

    @Override
    public boolean logOutUser(String token) {
        // TODO Auto-generated method stub
        return true;
    }

}
