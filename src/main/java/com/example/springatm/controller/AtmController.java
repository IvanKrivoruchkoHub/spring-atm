package com.example.springatm.controller;

import com.example.springatm.dto.AtmBanknotesDto;
import com.example.springatm.dto.GetMoneyFromAtmDto;
import com.example.springatm.entity.Atm;
import com.example.springatm.entity.BankAccount;
import com.example.springatm.entity.Banknote;
import com.example.springatm.service.AtmService;
import com.example.springatm.service.BankAccountService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController(value = "/atm")
public class AtmController {
    @Autowired
    private AtmService atmService;

    @Autowired
    private BankAccountService bankAccountService;

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
        BankAccount bankAccount = bankAccountService
                .findByNumberOfBankAccount(getMoneyFromAtmDto.getNumberOfAccount());
        Atm atm = atmService.findById(getMoneyFromAtmDto.getAtmId());
        if (bankAccount.getSum().compareTo(getMoneyFromAtmDto.getSum()) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not enough money on bankAccount. You have : " + bankAccount.getSum());
        }
        BigDecimal sumAtm = getSumFromMapOfBanknotes(atm.getBanknotes());
        if (sumAtm.compareTo(getMoneyFromAtmDto.getSum()) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not enough money on atm!");
        }
        if (!getMoneyFromAtmDto.getSum().remainder(new BigDecimal(100))
                .equals(new BigDecimal(0))) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Choose the sum that multiple of 100");
        }

        Map<Banknote, Long> result = new HashMap<>();
        List<Banknote> banknoteList = new ArrayList<>(atm.getBanknotes().keySet());
        banknoteList.sort(Collections.reverseOrder());
        BigDecimal tempSum = getMoneyFromAtmDto.getSum();
        for (Banknote banknote : banknoteList) {
            if (tempSum.equals(new BigDecimal(0))) {
                break;
            }
            Long countBanknotes = tempSum.divideAndRemainder(new BigDecimal(banknote.getValue()))[0].longValue();
            if (countBanknotes > atm.getBanknotes().get(banknote)) {
                countBanknotes = atm.getBanknotes().get(banknote);
            }
            result.put(banknote, countBanknotes);
            tempSum = tempSum.subtract(new BigDecimal(countBanknotes * banknote.getValue()));
            atm.subtractBanknotes(banknote, countBanknotes);
        }
        bankAccount.subtractMoney(getMoneyFromAtmDto.getSum());
        bankAccountService.save(bankAccount);
        atmService.save(atm);
        return result;
    }

    private BigDecimal getSumFromMapOfBanknotes(Map<Banknote, Long> mapOfBanknotes) {
        BigDecimal result = new BigDecimal(0);
        for (Map.Entry<Banknote, Long> banknoteEntry : mapOfBanknotes.entrySet()) {
            result = result.add(new BigDecimal(banknoteEntry
                    .getKey().getValue() * banknoteEntry.getValue()));
        }
        return result;
    }
}
