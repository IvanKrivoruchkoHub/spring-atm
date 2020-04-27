package com.example.springatm.controller;

import com.example.springatm.entity.Atm;
import com.example.springatm.entity.BankAccount;
import com.example.springatm.entity.Banknote;
import com.example.springatm.entity.Role;
import com.example.springatm.entity.User;
import com.example.springatm.service.AtmService;
import com.example.springatm.service.BankAccountService;
import com.example.springatm.service.UserService;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class InitController {
    @Autowired
    private AtmService atmService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {

        Atm atm = new Atm();
        atm.addBanknotes(Banknote.BANKNOTE100, 100L);
        atm.addBanknotes(Banknote.BANKNOTE200, 100L);
        atm.addBanknotes(Banknote.BANKNOTE500, 100L);

        atmService.save(atm);

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setSum(new BigDecimal("5000"));
        bankAccount1 = bankAccountService.save(bankAccount1);

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setSum(new BigDecimal("10000"));
        bankAccount2 = bankAccountService.save(bankAccount2);

        User user = new User();
        user.addBankAccount(bankAccount1);
        user.addBankAccount(bankAccount2);
        user.setLogin("user");
        user.addRole(Role.USER);
        user.setPassword(passwordEncoder.encode("user"));
        userService.save(user);

        User admin = new User();
        admin.setLogin("admin");
        admin.addRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode("admin"));
        userService.save(admin);
    }
}
