package com.example.springatm.entity;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "atms")
public class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "atms_banknotes")
    @MapKeyJoinColumn(name = "banknote_id")
    private Map<Banknote, Long> banknotes = new HashMap<>();

    public Long addBanknotes(Banknote banknote, Long count) {
        return banknotes.compute(banknote, (k, v) -> v == null ? count : v + count);
    }
}
