package com.example.springatm.service.impl;

import com.example.springatm.entity.Atm;
import com.example.springatm.repository.AtmRepository;
import com.example.springatm.service.AtmService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (atm.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Can't find ATM by id = " + id);
        }
        return atm.get();
    }
}
