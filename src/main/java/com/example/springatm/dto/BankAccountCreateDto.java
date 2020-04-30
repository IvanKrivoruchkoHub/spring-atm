package com.example.springatm.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BankAccountCreateDto {
    private String login;
    private BigDecimal sum;
}
