package com.example.springatm.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AddMoneyToAccountDto {
    private BigDecimal sum;
    private String numberOfAccount;
}
