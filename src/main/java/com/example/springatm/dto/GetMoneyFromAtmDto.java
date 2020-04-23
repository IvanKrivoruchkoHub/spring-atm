package com.example.springatm.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GetMoneyFromAtmDto {
    private BigDecimal sum;
    private String numberOfAccount;
    private Long atmId;
}
