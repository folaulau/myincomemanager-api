package com.incomemanager.api.entity.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserDAOImp implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<User> findByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
