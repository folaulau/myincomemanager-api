package com.incomemanager.api.dataloader;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.account.AccountRepository;
import com.incomemanager.api.entity.user.User;
import com.incomemanager.api.entity.user.UserRepository;
import com.incomemanager.api.entity.user.UserType;
import com.incomemanager.api.utils.RandomGeneratorUtils;

import lombok.extern.slf4j.Slf4j;

// @DependsOn(value = {"groomerDataLoader", "parentDataLoader"})
@Slf4j
@Profile(value = {"local", "dev"})
@Component
public class AccountDataLoader implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository    userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Account account = Account.builder().id(1L).build();

        accountRepository.saveAndFlush(account);

        String firstName = "Folau";
        String lastName = "Kaveinga";
        String email = (firstName+lastName).toLowerCase()+"@gmail.com";
        User user = User.builder()
                .id(1L)
                .firstName(firstName)
                .lastName(lastName)
                .type(UserType.user)
                .email(email)
                .phoneNumber(RandomGeneratorUtils.getRandomPhone())
                .account(account)
                .build();

        userRepository.saveAndFlush(user);
    }

}
