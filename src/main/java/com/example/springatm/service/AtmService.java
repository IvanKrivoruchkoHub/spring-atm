package com.example.springatm.service;

import com.example.springatm.entity.Atm;
import com.example.springatm.entity.Banknote;
import java.math.BigDecimal;
import java.util.Map;

public interface AtmService {
    Atm save(Atm atm);

    Atm findById(Long id);

    Map<Banknote, Long> getBanknotesFromAtm(BigDecimal sum, String numberOfAccount, Long atmId);
}
