package com.incomemanager.api.entity.user;

import java.util.Optional;

public interface UserDAO {

    Optional<User> findByUuid(String uuid);

    Optional<User> findByEmail(String email);

    User save(User user);
}
