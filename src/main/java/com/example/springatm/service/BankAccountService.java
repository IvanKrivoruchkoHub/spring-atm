package com.example.springatm.service;

import com.example.springatm.entity.BankAccount;

public interface BankAccountService {
    BankAccount save(BankAccount bankAccount);

    BankAccount findById(Long id);

    BankAccount findByNumberOfBankAccount(String numberOfBankAccount);
}
