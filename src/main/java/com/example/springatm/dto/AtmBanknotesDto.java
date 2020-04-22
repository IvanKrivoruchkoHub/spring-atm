package com.example.springatm.dto;

import com.example.springatm.entity.Banknote;
import lombok.Data;
import java.util.Map;

@Data
public class AtmBanknotesDto {
    private Long atmId;
    private Map<Banknote, Long> banknoteMap;
}
