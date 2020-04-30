package com.example.springatm.repository;

import com.example.springatm.entity.BankAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByNumberOfBankAccount(String numberOfBankAccount);
}
