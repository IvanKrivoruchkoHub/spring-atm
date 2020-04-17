package com.example.springatm.service.impl;

import com.example.springatm.entity.Banknote;
import com.example.springatm.repository.BanknoteRepository;
import com.example.springatm.service.BanknoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanknoteServiceImpl implements BanknoteService {
    @Autowired
    private BanknoteRepository banknoteRepository;

    @Override
    public Banknote save(Banknote banknote) {
        return banknoteRepository.save(banknote);
    }
}
