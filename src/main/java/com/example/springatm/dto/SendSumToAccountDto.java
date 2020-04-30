package com.example.springatm.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SendSumToAccountDto {
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal sum;
}
