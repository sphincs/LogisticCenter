package com.sphincs.rest;

import com.sphincs.domain.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverDataFixture {

    public static Driver getNewDriver() {
        Driver driver = new Driver();
        driver.setName("Ralph");
        driver.setAge(33);
        return driver;
    }

    public static Driver getBadNewDriver() {
        Driver driver = new Driver();
        driver.setName("Ralph");
        driver.setAge(10);
        return driver;
    }

    public static Driver getExistingDriver(Long id) {
        Driver driver = new Driver();
        driver.setId(id);
        driver.setName("Mike");
        driver.setAge(35);
        return driver;
    }

    public static Driver getExistingDriverByName(String name) {
        Driver driver = new Driver();
        driver.setId(2L);
        driver.setName(name);
        driver.setAge(33);
        return driver;
    }

    public static List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(getExistingDriver(1L));
        drivers.add(getExistingDriverByName("Bobby"));
        return drivers;
    }

}
