package com.example.springatm.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BankAccountCreateDto {
    private String login;
    private BigDecimal sum;
}
