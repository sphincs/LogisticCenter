package com.sphincs.rest;


import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
import com.sphincs.domain.Driver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverDataFixture {

    public static Driver getNewDriver() {
        Driver driver = new Driver();
        driver.setName("Ralph");
        driver.setAge(33);
        Set<Category> categories = new HashSet<>();
        categories.add(Category.C);
        categories.add(Category.D);
        driver.setCategories(categories);
        driver.setCar(Car.DAF);
        driver.setCarNumber("1234-ab1");
        driver.setFuelRate100();
        return driver;
    }

    public static Long addNewDriver() {
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Ralph");
        driver.setAge(33);
        Set<Category> categories = new HashSet<>();
        categories.add(Category.C);
        categories.add(Category.D);
        driver.setCategories(categories);
        driver.setCar(Car.DAF);
        driver.setCarNumber("1234-ab1");
        driver.setFuelRate100();
        return driver.getId();
    }

    public static Driver getExistingDriver(Long id) {
        Driver driver = new Driver();
        driver.setId(id);
        driver.setName("Ralph");
        driver.setAge(33);
        Set<Category> categories = new HashSet<>();
        categories.add(Category.D);
        driver.setCategories(categories);
        driver.setCar(Car.DAF);
        driver.setCarNumber("1234-ab1");
        driver.setFuelRate100();
        return driver;
    }

    public static Driver getExistingDriverByName(String name) {
        Driver driver = new Driver();
        driver.setId(2L);
        driver.setName(name);
        driver.setAge(40);
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        driver.setCategories(categories);
        driver.setCar(Car.BMW);
        driver.setCarNumber("9876-ab1");
        driver.setFuelRate100();
        return driver;
    }

    public static List<Driver> getExistingDriversByCar(Car car) {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(getExistingDriver(1L));
        drivers.add(getExistingDriver(2L));
        return drivers;
    }

    public static List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(getExistingDriver(1L));
        drivers.add(getExistingDriverByName("Mike"));
        return drivers;
    }
}
