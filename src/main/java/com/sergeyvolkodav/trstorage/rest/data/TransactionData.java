package com.sergeyvolkodav.trstorage.rest.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergeyvolkodav.trstorage.utils.validation.CheckMoney;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class TransactionData {

    @JsonIgnore
    private Long id;

    private Double amount;

    private String type;

    private Long parentId;


    public TransactionData(Long id, Double amount, String type, Long parentId) {
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
        this.id = id;
    }

    public TransactionData() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @NotNull(message = "Amount cannot be null")
    @CheckMoney(message = "Amount should be set in money format")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @NotBlank(message = "Type cannot be blank")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
