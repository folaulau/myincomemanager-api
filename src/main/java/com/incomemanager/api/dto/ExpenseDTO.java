package com.incomemanager.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.expense.ExpenseType;
import com.incomemanager.api.entity.income.PayPeriod;
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

/**
 * This class keeps track of expenses that user will have per month.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExpenseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;

    private String            uuid;

    private ExpenseType       type;

    private String            name;

    private Double            amount;

    // 1-28
    private Integer           monthlyDueDay;

    private AccountDTO        account;

    private boolean           deleted;

    private LocalDateTime     createdAt;

    private LocalDateTime     updatedAt;

}
