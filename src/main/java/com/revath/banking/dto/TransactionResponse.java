package com.revath.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.revath.banking.entity.TransactionType;

public class TransactionResponse {
	private TransactionType transactionType;
    private BigDecimal amount;
    private String referenceAccountNumber;
    private String description;
    private LocalDateTime transactionTime;
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReferenceAccountNumber() {
        return referenceAccountNumber;
    }

    public void setReferenceAccountNumber(String referenceAccountNumber) {
        this.referenceAccountNumber = referenceAccountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

}
