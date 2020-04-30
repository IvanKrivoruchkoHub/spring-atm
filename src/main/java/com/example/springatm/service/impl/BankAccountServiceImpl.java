package com.example.springatm.service.impl;

import com.example.springatm.entity.BankAccount;
import com.example.springatm.repository.BankAccountRepository;
import com.example.springatm.service.BankAccountService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount save(BankAccount bankAccount) {
        BankAccount account = bankAccountRepository.save(bankAccount);
        if (account.getNumberOfBankAccount() == null) {
            account.setNumberOfBankAccount(String.format("%019d", account.getId()));
            account = bankAccountRepository.save(bankAccount);
        }
        return account;
    }

    @Override
    public BankAccount findById(Long id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        if (bankAccount.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Can't find BankAccount by id = " + id);
        }
        return bankAccount.get();
    }

    @Override
    public BankAccount findByNumberOfBankAccount(String numberOfBankAccount) {
        Optional<BankAccount> bankAccount =
                bankAccountRepository.findByNumberOfBankAccount(numberOfBankAccount);
        if (bankAccount.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Can't find BankAccount by number = " + numberOfBankAccount);
        }
        return bankAccount.get();
    }
}
