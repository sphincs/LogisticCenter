package com.sphincs.domain;

public enum Car {

    LADA(5.5),
    GAZ(6.8),
    BMW(7.5),
    MERSEDES(7.2),
    RENAULT(12.0),
    PEUGEOT(13.8),
    FORD(6.2),
    VOLKSWAGEN(7.2),
    SCANIA(14.6),
    KAWASAKI(4.0),
    DAF(13.5);

    private double fuelRate;
    private Car(double fuelRate) {
        this.fuelRate = fuelRate;
    }

    public double getFuelRate() {
        return fuelRate;
    }

    public static Car getByIndex(int index) {
        return values()[index];
    }
}
