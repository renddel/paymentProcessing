package com.webapp.paymentprocessing.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Conditional;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long amount;
    private String debtorIban;
    private String creditorIban;
    private Type type;
    private Currency currency;
    private LocalDateTime creationDate;
    private boolean active;
    private Double fee;

    private String details;

    private String creditorBankBicCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreditorBankBicCode() {
        return creditorBankBicCode;
    }

    public void setCreditorBankBicCode(String creditorBankBicCode) {
        this.creditorBankBicCode = creditorBankBicCode;
    }
}
