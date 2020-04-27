package com.example.springatm.controller;

import com.example.springatm.dto.AtmBanknotesDto;
import com.example.springatm.dto.GetMoneyFromAtmDto;
import com.example.springatm.entity.Atm;
import com.example.springatm.entity.Banknote;
import com.example.springatm.service.AtmService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/atm")
public class AtmController {
    @Autowired
    private AtmService atmService;

    @PostMapping(value = "/addingBanknotes")
    public void addBanknotes(@RequestBody AtmBanknotesDto atmBanknotesDto) {
        Atm atm = atmService.findById(atmBanknotesDto.getAtmId());
        for (Map.Entry<Banknote, Long> banknoteEntry : atmBanknotesDto.getBanknoteMap().entrySet()) {
            atm.addBanknotes(banknoteEntry.getKey(), banknoteEntry.getValue());
        }
        atmService.save(atm);
    }

    @PostMapping(value = "/gettingBanknotes")
    public Map<Banknote, Long> getBanknotesFromAtm(@RequestBody GetMoneyFromAtmDto getMoneyFromAtmDto) {
        return atmService.getBanknotesFromAtm(getMoneyFromAtmDto.getSum(),
                getMoneyFromAtmDto.getNumberOfAccount(),
                getMoneyFromAtmDto.getAtmId());
    }
}
