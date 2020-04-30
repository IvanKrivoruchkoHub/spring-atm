package com.example.springatm.entity;

import lombok.Data;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
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
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "banknote_count")
    private Map<Banknote, Long> banknotes = new LinkedHashMap<>();

    public Atm() {
        banknotes.put(Banknote.BANKNOTE500, 0L);
        banknotes.put(Banknote.BANKNOTE200, 0L);
        banknotes.put(Banknote.BANKNOTE100, 0L);
    }

    public Long addBanknotes(Banknote banknote, Long count) {
        return banknotes.compute(banknote, (k, v) -> v == null ? count : v + count);
    }

    public Long subtractBanknotes(Banknote banknote, Long count) {
        return banknotes.compute(banknote, (k, v) -> v != null ? v - count : null);
    }
}
