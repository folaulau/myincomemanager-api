package com.incomemanager.api.entity.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incomemanager.api.jwt.JwtTokenService;

@Slf4j
@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public String generateApiToken(User user) {
        return null;
    }

    @Override
    public AuthenticationResponseDTO login(AuthenticatorDTO authenticatorDTO) {
        AuthenticationResponseDTO auth = new AuthenticationResponseDTO();
        User user = userRepository.findById(authenticatorDTO.getUserId()).get();
        String jwtToken = jwtTokenService.generateToken(user);
        auth.setFirstName(user.getFirstName());
        auth.setLastName(user.getLastName());
        auth.setEmail(user.getEmail());
        auth.setUuid(user.getUuid());
        auth.setId(user.getId());
        auth.setToken(jwtToken);
        return auth;
    }
}
