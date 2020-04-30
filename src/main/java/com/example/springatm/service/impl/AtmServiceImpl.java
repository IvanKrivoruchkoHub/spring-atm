package com.example.springatm.service.impl;

import com.example.springatm.entity.Atm;
import com.example.springatm.entity.BankAccount;
import com.example.springatm.entity.Banknote;
import com.example.springatm.repository.AtmRepository;
import com.example.springatm.service.AtmService;
import com.example.springatm.service.BankAccountService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AtmServiceImpl implements AtmService {
    @Autowired
    private AtmRepository atmRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    public Atm save(Atm atm) {
        return atmRepository.save(atm);
    }

    @Override
    public Atm findById(Long id) {
        Optional<Atm> atm = atmRepository.findById(id);
        if (atm.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Can't find ATM by id = " + id);
        }
        return atm.get();
    }

    @Override
    public Map<Banknote, Long> getBanknotesFromAtm(BigDecimal sum,
                                                   String numberOfAccount, Long atmId) {
        BankAccount bankAccount = bankAccountService
                .findByNumberOfBankAccount(numberOfAccount);
        Atm atm = findById(atmId);
        if (bankAccount.getSum().compareTo(sum) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not enough money on bankAccount. You have : " + bankAccount.getSum());
        }
        BigDecimal sumAtm = getSumFromMapOfBanknotes(atm.getBanknotes());
        if (sumAtm.compareTo(sum) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not enough money on atm!");
        }
        if (!sum.remainder(new BigDecimal(100))
                .equals(new BigDecimal(0))) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Choose the sum that multiple of 100");
        }

        Map<Banknote, Long> result = new HashMap<>();
        List<Banknote> banknoteList = new ArrayList<>(atm.getBanknotes().keySet());
        banknoteList.sort(Collections.reverseOrder());
        BigDecimal tempSum = sum;
        for (Banknote banknote : banknoteList) {
            if (tempSum.equals(new BigDecimal(0))) {
                break;
            }
            Long countBanknotes = tempSum
                    .divideAndRemainder(new BigDecimal(banknote.getValue()))[0].longValue();
            if (countBanknotes > atm.getBanknotes().get(banknote)) {
                countBanknotes = atm.getBanknotes().get(banknote);
            }
            result.put(banknote, countBanknotes);
            tempSum = tempSum.subtract(new BigDecimal(countBanknotes * banknote.getValue()));
            atm.subtractBanknotes(banknote, countBanknotes);
        }
        bankAccount.subtractMoney(sum);
        bankAccountService.save(bankAccount);
        save(atm);
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
