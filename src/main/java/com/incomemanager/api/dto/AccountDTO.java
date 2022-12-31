package com.incomemanager.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;

    private String            uuid;

    private LocalDateTime     createdAt;

    private LocalDateTime     updatedAt;

    public String toJson() {
        return ObjectUtils.toJson(this);
    }

}
