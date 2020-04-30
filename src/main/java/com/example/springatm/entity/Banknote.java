package com.example.springatm.entity;

public enum Banknote {
    BANKNOTE100(100), BANKNOTE200(200), BANKNOTE500(500);

    private Integer value;

    Banknote(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
