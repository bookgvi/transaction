package com.example.transaction.dto;

import java.math.BigDecimal;

public class TransferDTO {
    private long sourceAccountId;
    private long destAccountId;
    private BigDecimal transferAmount;
    private String transferCurrency;

    public long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public long getDestAccountId() {
        return destAccountId;
    }

    public void setDestAccountId(long destAccountId) {
        this.destAccountId = destAccountId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferCurrency() {
        return transferCurrency;
    }

    public void setTransferCurrency(String transferCurrency) {
        this.transferCurrency = transferCurrency;
    }
}
