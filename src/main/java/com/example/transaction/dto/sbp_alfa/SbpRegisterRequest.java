package com.example.transaction.dto.sbp_alfa;

import java.math.BigDecimal;

public class SbpRegisterRequest {
    private String orderNumber;
    private BigDecimal amount;
    private final String returnUrl = "http://return.url";
    private final String userName = "rgs_0-api";
    private final String password = "rgs_0*?1";

    public SbpRegisterRequest() {
    }

    public SbpRegisterRequest(String orderNumber, BigDecimal amount) {
        this.orderNumber = orderNumber;
        this.amount = amount;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
