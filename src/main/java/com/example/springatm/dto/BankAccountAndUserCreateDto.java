package com.example.springatm.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BankAccountAndUserCreateDto {
    private String login;
    private String password;
    private BigDecimal sum;
}
