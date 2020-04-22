package com.example.springatm.service.impl;

import com.example.springatm.entity.Atm;
import com.example.springatm.repository.AtmRepository;
import com.example.springatm.service.AtmService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtmServiceImpl implements AtmService {
    @Autowired
    private AtmRepository atmRepository;

    @Override
    public Atm save(Atm atm) {
        return atmRepository.save(atm);
    }

    @Override
    public Atm findById(Long id) {
        Optional<Atm> atm = atmRepository.findById(id);
        return atm.orElse(null);
    }
}
