package com.example.springatm.repository;

import com.example.springatm.entity.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
}
