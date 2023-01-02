package com.incomemanager.api.entity.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUuid(String uuid);
    
    Optional<User> findByEmail(String email);
}
