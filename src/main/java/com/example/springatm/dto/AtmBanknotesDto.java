package com.example.springatm.dto;

import com.example.springatm.entity.Banknote;
import java.util.Map;
import lombok.Data;

@Data
public class AtmBanknotesDto {
    private Long atmId;
    private Map<Banknote, Long> banknoteMap;
}
