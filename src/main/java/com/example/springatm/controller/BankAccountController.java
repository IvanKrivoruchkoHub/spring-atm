package com.example.springatm.controller;

import com.example.springatm.dto.AddMoneyToAccountDto;
import com.example.springatm.dto.BankAccountAndUserCreateDto;
import com.example.springatm.dto.BankAccountCreateDto;
import com.example.springatm.dto.SendSumToAccountDto;
import com.example.springatm.entity.BankAccount;
import com.example.springatm.entity.Role;
import com.example.springatm.entity.User;
import com.example.springatm.service.BankAccountService;
import com.example.springatm.service.UserService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController(value = "/bankAccount")
public class BankAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/newUserAndBankAccount")
    public Long createBankAccountAndUser(@RequestBody BankAccountAndUserCreateDto bankAccountAndUserCreateDto) {
        if (userService.findByLogin(bankAccountAndUserCreateDto.getLogin()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "This user has already exist");
        }
        User user = new User();
        user.setLogin(bankAccountAndUserCreateDto.getLogin());
        user.setPassword(bankAccountAndUserCreateDto.getPassword());
        user.addRole(Role.USER);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setSum(bankAccountAndUserCreateDto.getSum());
        bankAccount = bankAccountService.save(bankAccount);
        user.addBankAccount(bankAccount);
        userService.save(user);
        return bankAccount.getId();
    }

    @PostMapping("/newBankAccountForUser")
    public Long addBankAccountForUser(@RequestBody BankAccountCreateDto bankAccountCreateDto) {
        User user = userService.findByLogin(bankAccountCreateDto.getLogin());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "User don't exist");
        }
        BankAccount bankAccount = new BankAccount();
        bankAccount.setSum(bankAccountCreateDto.getSum());
        bankAccount = bankAccountService.save(bankAccount);
        user.addBankAccount(bankAccount);
        userService.save(user);
        return bankAccount.getId();
    }


    @PutMapping(value = "/addingSum")
    public BigDecimal addMoneyToAccount(@RequestBody AddMoneyToAccountDto addMoneyToAccountDto) {
        BankAccount bankAccount = bankAccountService.findById(addMoneyToAccountDto.getBankAccountId());
        if (bankAccount == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Can't find account");
        }
        bankAccount.addMoney(addMoneyToAccountDto.getSum());
        return bankAccountService.save(bankAccount).getSum();
    }

    @PostMapping(value = "/sendingSum")
    public BigDecimal sendSumToAccount(@RequestBody SendSumToAccountDto sendSumToAccountDto) {
        BankAccount fromAccount = bankAccountService.findById(sendSumToAccountDto.getFromAccountId());
        BankAccount toAccount = bankAccountService.findById(sendSumToAccountDto.getToAccountId());
        if (fromAccount == null || toAccount == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Can't find accounts");
        }
        if (fromAccount.getSum().compareTo(sendSumToAccountDto.getSum()) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Not enough money to send");
        }
        BigDecimal beforeSendFromAccountSum = fromAccount.getSum();
        BigDecimal beforeSendToAccountSum = toAccount.getSum();
        try {
            fromAccount.subtractMoney(sendSumToAccountDto.getSum());
            toAccount.addMoney(sendSumToAccountDto.getSum());
            bankAccountService.save(fromAccount);
            bankAccountService.save(toAccount);
        } catch (Exception e) {
            if (!fromAccount.getSum().equals(beforeSendFromAccountSum)) {
                fromAccount.addMoney(sendSumToAccountDto.getSum());
                bankAccountService.save(fromAccount);
            }
            if (!toAccount.getSum().equals(beforeSendToAccountSum)) {
                toAccount.subtractMoney(sendSumToAccountDto.getSum());
                bankAccountService.save(fromAccount);
            }
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Can't send money", e);
        }
        return fromAccount.getSum();
    }
}
