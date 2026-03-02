package com.revath.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferResponse {

    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal transferredAmount;
    private BigDecimal senderBalance;
    private LocalDateTime transactionTime;

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public BigDecimal getTransferredAmount() {
        return transferredAmount;
    }

    public void setTransferredAmount(BigDecimal transferredAmount) {
        this.transferredAmount = transferredAmount;
    }

    public BigDecimal getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(BigDecimal senderBalance) {
        this.senderBalance = senderBalance;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}