package com.incomemanager.api.entity.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.PrePersist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.user.role.Role;
import com.incomemanager.api.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@DynamicUpdate
@Entity
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?", check = ResultCheckStyle.NONE)
@Where(clause = "deleted = false")
@Table(name = "users", indexes = {@Index(columnList = "uuid"), @Index(columnList = "deleted")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long              id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String            uuid;

    @Column(name = "first_name")
    private String            firstName;

    @Column(name = "last_name")
    private String            lastName;

    @NotEmpty
    @Column(name = "email", unique = true)
    private String            email;

    @Column(name = "phone_number")
    private String            phoneNumber;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account           account;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType          type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus        status;

    @Column(name = "third_party_name")
    private String            thirdPartyName;
    /**
     * Social platforms(facebook, google, etc) don't give email<br>
     * Now create a temp email for now
     */
    @Column(name = "email_temp")
    private boolean           emailTemp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role              role;

    @Column(name = "deleted", nullable = false)
    private boolean           deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime     createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime     updatedAt;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.id).append(this.uuid).append(this.firstName).append(this.lastName).append(this.email).append(this.phoneNumber).append(this.account).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        User other = (User) obj;
        return new EqualsBuilder().append(this.id, other.id)
                .append(this.uuid, other.uuid)
                .append(this.firstName, other.firstName)
                .append(this.lastName, other.lastName)
                .append(this.email, other.email)
                .append(this.phoneNumber, other.phoneNumber)
                .append(this.account, other.account)
                .isEquals();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ToStringBuilder.reflectionToString(this);
    }

    @PrePersist
    private void preCreate() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.uuid = "user-" + UUID.randomUUID().toString();
        }

    }

    public String toJson() {
        return ObjectUtils.toJson(this);
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            fullName.append(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(lastName);
        }
        return fullName.toString();
    }

    public String getRoleAsString() {
        if (this.role == null) {
            return null;
        }
        return this.role.getUserType().name();
    }

    public boolean isActive() {
        return Optional.ofNullable(this.status).orElse(UserStatus.NONE).equals(UserStatus.ACTIVE);
    }

}
