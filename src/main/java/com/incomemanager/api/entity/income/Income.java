package com.incomemanager.api.entity.income;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incomemanager.api.entity.account.Account;
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
@DynamicUpdate
@Entity
@SQLDelete(sql = "UPDATE incomes SET deleted = 'T' WHERE id = ?", check = ResultCheckStyle.NONE)
@Where(clause = "deleted = 'F'")
@Table(name = "incomes", indexes = {@Index(columnList = "uuid"), @Index(columnList = "deleted")})
public class Income implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_type")
    private PayType payType;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_period")
    private PayPeriod payPeriod;

    // pay check net amount(after taxes)
    @Column(name = "pay_period_net_amount")
    private BigDecimal payPeriodNetAmount;
    /**
     * yearly total income
     */
    @Column(name = "yearly_total")
    private BigDecimal yearlyTotal;

    @Column(name = "company_name")
    private String companyName;

    // position like Software Developer
    @Column(name = "position")
    private String position;

    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

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
            this.uuid = "income-" + UUID.randomUUID().toString();
        }
    }
}
