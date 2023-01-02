package com.incomemanager.api.security;

import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.entity.user.User;
import com.incomemanager.api.jwt.JwtPayload;

public interface AuthenticationService {

    AuthenticationResponseDTO authenticate(User user);

    boolean authorizeRequest(String token, JwtPayload jwtPayload);

    boolean logOutUser(String token);
}
