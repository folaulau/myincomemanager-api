package com.incomemanager.api.entity.goal;


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
@DynamicUpdate
@Entity
@SQLDelete(sql = "UPDATE goals SET deleted = 'T' WHERE id = ?", check = ResultCheckStyle.NONE)
@Where(clause = "deleted = 'F'")
@Table(name = "goals", indexes = {@Index(columnList = "uuid"), @Index(columnList = "deleted")})
public class Goal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "target_amount")
    private Double targetAmount;

    @Column(name = "current_amount")
    private Double currentAmount;
    
    @Column(name = "amount_per_paycheck")
    private Double amountPerPaycheck;

    // where the goal should be reached
    @Column(name = "deadline", nullable = true)
    private LocalDate deadline;

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
            this.uuid = "goal-" + UUID.randomUUID().toString();
        }
    }
}
