package com.incomemanager.api.entity.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.user.User;
import com.incomemanager.api.entity.user.UserType;
import com.incomemanager.api.utils.ObjectUtils;

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
import java.math.RoundingMode;
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
@SQLDelete(sql = "UPDATE transactions SET deleted = true WHERE id = ?", check = ResultCheckStyle.NONE)
@Where(clause = "deleted = false")
@Table(name = "transactions", indexes = {@Index(columnList = "uuid"), @Index(columnList = "deleted")})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long              id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String            uuid;

    // pay check net amount(after taxes)
    @Column(name = "name")
    private String            name;

    @Column(name = "price")
    private Double            price;

    @Column(name = "quantity")
    private Integer           quantity;

    @Column(name = "total")
    private Double            total;

//    @Lob
    @Column(name = "note", columnDefinition="TEXT")
    private String            note;

    // when transaction actually happened which can be the same at createdAt
    @Column(name = "datetime", nullable = false)
    private LocalDateTime     datetime;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User              user;

    @Column(name = "deleted", nullable = false)
    private boolean           deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime     createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime     updatedAt;

    @PrePersist
    private void preCreate() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.uuid = "transaction-" + UUID.randomUUID().toString();
        }
    }

    public String toJson() {
        return ObjectUtils.toJson(this);
    }

    public void generateTotal() {
        if (this.price == null) {
            return;
        }
        if (this.quantity == null) {
            this.quantity = 1;
        }

        this.total = this.price * this.quantity;

        this.total = BigDecimal.valueOf(this.price).multiply(BigDecimal.valueOf(this.quantity)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
