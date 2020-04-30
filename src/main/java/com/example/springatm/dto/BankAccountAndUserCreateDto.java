package com.example.springatm.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BankAccountAndUserCreateDto {
    private String login;
    private String password;
    private BigDecimal sum;
}
