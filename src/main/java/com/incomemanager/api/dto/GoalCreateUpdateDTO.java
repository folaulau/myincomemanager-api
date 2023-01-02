package com.incomemanager.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.user.User;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GoalCreateUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            uuid;

    private String            title;

    private String            description;

    private Double            targetAmount;

    private Double            currentAmount;

    private Double            amountPerPaycheck;

    private LocalDate         deadline;

}
