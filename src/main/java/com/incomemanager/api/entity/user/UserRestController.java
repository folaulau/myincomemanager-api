package com.incomemanager.api.entity.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.dto.AuthenticatorDTO;
import com.incomemanager.api.dto.UserSignUpDTO;
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

    @Operation(summary = "Sign Up", description = "sign up with email and password")
    @PostMapping(value = "/users/signup")
    public ResponseEntity<AuthenticationResponseDTO> signupWithEmailAndPassword(@RequestBody UserSignUpDTO userSignUpDTO) {
        log.info("signupWithEmailAndPassword={}", ObjectUtils.toJson(userSignUpDTO));

        AuthenticationResponseDTO authenticationResponseDTO = userService.signupWithEmailAndPassword(userSignUpDTO);

        return new ResponseEntity<>(authenticationResponseDTO, OK);
    }

}
