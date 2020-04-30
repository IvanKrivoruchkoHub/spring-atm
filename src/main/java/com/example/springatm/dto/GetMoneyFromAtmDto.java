package com.example.springatm.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class GetMoneyFromAtmDto {
    private BigDecimal sum;
    private String numberOfAccount;
    private Long atmId;
}
