package com.incomemanager.api.entity.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.dto.AuthenticatorDTO;
import com.incomemanager.api.dto.UserDTO;
import com.incomemanager.api.dto.UserProfileUpdateDTO;
import com.incomemanager.api.utils.ObjectUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Users", description = "User Operations")
@Slf4j
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Authenticate", description = "authenticate")
    @PostMapping(value = "/users/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticatorDTO authenticatorDTO) {
        log.info("authenticate={}", ObjectUtils.toJson(authenticatorDTO));

        AuthenticationResponseDTO authenticationResponseDTO = userService.authenticate(authenticatorDTO);

        return new ResponseEntity<>(authenticationResponseDTO, OK);
    }
    
    @Operation(summary = "Update Profile", description = "update profile")
    @PutMapping(value = "/users/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserProfileUpdateDTO userProfileUpdateDTO) {
        log.info("updateProfile={}", ObjectUtils.toJson(userProfileUpdateDTO));

        UserDTO userDTO = userService.updateProfile(userProfileUpdateDTO);

        return new ResponseEntity<>(userDTO, OK);
    }

}
