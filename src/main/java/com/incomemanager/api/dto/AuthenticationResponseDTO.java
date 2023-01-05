package com.incomemanager.api.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.incomemanager.api.entity.account.AccountStatus;
import com.incomemanager.api.entity.account.SignUpStatus;
import com.incomemanager.api.entity.user.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@JsonInclude(value = Include.NON_NULL)
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Jwt token used for API calls.
     */
    private String            token;

    /**
     * user id
     */
    private Long              id;

    /**
     * user uuid
     */
    private String            uuid;

    private String            email;

    private Long              phoneNumber;

    private Role              role;

    private String            status;

    private String            accountUuid;

    private AccountStatus     accountStatus;

    private SignUpStatus      signUpStatus;

}
