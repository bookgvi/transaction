package com.example.transaction.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstname;
    private String lastname;
    private String middlename;
    private BigDecimal amount_amt;

    @Enumerated(EnumType.STRING)
    private Currency amount_cur;


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public BigDecimal getAmount_amt() {
        return amount_amt;
    }

    public void setAmount_amt(BigDecimal amount_amt) {
        this.amount_amt = amount_amt;
    }

    public long getId() {
        return id;
    }

    public Currency getAmount_cur() {
        return amount_cur;
    }

    public void setAmount_cur(Currency amount_cur) {
        this.amount_cur = amount_cur;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
