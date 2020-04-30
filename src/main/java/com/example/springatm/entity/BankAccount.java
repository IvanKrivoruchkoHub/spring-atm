package com.example.springatm.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numberOfBankAccount;
    private BigDecimal sum;

    public BigDecimal addMoney(BigDecimal addedSum) {
        sum = sum.add(addedSum);
        return sum;
    }

    public BigDecimal subtractMoney(BigDecimal subtractSum) {
        sum = sum.subtract(subtractSum);
        return sum;
    }
}
