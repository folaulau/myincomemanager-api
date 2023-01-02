package com.incomemanager.api.entity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incomemanager.api.dto.UserProfileUpdateDTO;
import com.incomemanager.api.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserValidatorServiceImp implements UserValidatorService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User validateProfileUpdate(UserProfileUpdateDTO userProfileUpdateDTO) {
        String uuid = userProfileUpdateDTO.getUuid();
        User user = userDAO.findByUuid(uuid).orElseThrow(() -> new ApiException("User not found", "user not found for uuid=" + uuid));

        return user;
    }

}
