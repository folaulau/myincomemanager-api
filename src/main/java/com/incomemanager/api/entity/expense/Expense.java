package com.incomemanager.api.entity.expense;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incomemanager.api.entity.account.Account;
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
@DynamicUpdate
@Entity
@SQLDelete(sql = "UPDATE expenses SET deleted = true WHERE id = ?", check = ResultCheckStyle.NONE)
@Where(clause = "deleted = false")
@Table(name = "expenses", indexes = {@Index(columnList = "uuid"), @Index(columnList = "deleted")})
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type", nullable = true)
    private ExpenseType type;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    //1-28
    @Column(name = "monthly_due_day", nullable = true)
    private Integer monthlyDueDay;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void preCreate() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.uuid = "expense-" + UUID.randomUUID().toString();
        }
    }
}
