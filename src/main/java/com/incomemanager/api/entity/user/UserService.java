package com.incomemanager.api.entity.user;

public interface UserService {
    String generateApiToken(User user);

    AuthenticationResponseDTO login(AuthenticatorDTO authenticatorDTO);
}
