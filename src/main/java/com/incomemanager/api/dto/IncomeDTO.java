package com.incomemanager.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.income.PayPeriod;
import com.incomemanager.api.entity.income.PayType;
import com.incomemanager.api.entity.user.User;
import com.incomemanager.api.entity.user.UserType;
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
public class IncomeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;

    private String            uuid;

    private PayType           payType;

    private PayPeriod         payPeriod;

    private Double            payPeriodNetAmount;

    private Double            yearlyTotal;

    private String            companyName;

    private String            position;

    private LocalDate         startDate;

    private LocalDate         endDate;

    private boolean           deleted;

    private LocalDateTime     createdAt;

    private LocalDateTime     updatedAt;

}
