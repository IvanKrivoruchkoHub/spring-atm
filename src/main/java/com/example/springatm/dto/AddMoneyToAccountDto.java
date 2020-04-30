package com.example.springatm.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddMoneyToAccountDto {
    private BigDecimal sum;
    private String numberOfAccount;
}
