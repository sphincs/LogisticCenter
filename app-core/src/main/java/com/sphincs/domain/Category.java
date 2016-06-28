package com.sphincs.domain;

public enum Category {

    A,
    B,
    C,
    D,
    CE,
    DE;

    public static Category getByIndex(int index) {
        return values()[index];
    }

}
