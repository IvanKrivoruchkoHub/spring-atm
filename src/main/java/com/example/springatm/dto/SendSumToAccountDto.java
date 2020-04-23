package com.example.springatm.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SendSumToAccountDto {
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal sum;
}
