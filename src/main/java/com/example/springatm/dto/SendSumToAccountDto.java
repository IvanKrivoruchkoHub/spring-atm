package com.example.springatm.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SendSumToAccountDto {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal sum;
}
