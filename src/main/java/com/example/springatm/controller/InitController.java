package com.example.springatm.controller;

import com.example.springatm.entity.Atm;
import com.example.springatm.entity.Banknote;
import com.example.springatm.service.AtmService;
import com.example.springatm.service.BankAccountService;
import com.example.springatm.service.BanknoteService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InitController {
    @Autowired
    private AtmService atmService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BanknoteService banknoteService;

    @PostConstruct
    private void init() {
        Banknote banknote100 = new Banknote();
        banknote100.setValue(100);
        Banknote banknote200 = new Banknote();
        banknote200.setValue(200);
        Banknote banknote500 = new Banknote();
        banknote500.setValue(500);
        banknote100 = banknoteService.save(banknote100);
        banknote200 = banknoteService.save(banknote200);
        banknote500 = banknoteService.save(banknote500);
        Atm atm = new Atm();
        Map<Banknote, Long> banknotesMap= new HashMap<>();
        banknotesMap.put(banknote100, 100L);
        banknotesMap.put(banknote200, 100L);
        banknotesMap.put(banknote500, 100L);

        atm.setBanknotes(banknotesMap);
        atm = atmService.save(atm);
        atm.addBanknotes(banknote100, 200L);
        atm = atmService.save(atm);
    }
}
