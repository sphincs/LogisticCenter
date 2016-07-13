package com.sphincs.rest;


import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
import com.sphincs.domain.Driver;

import java.util.HashSet;
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

    public static Driver getExistingDriver(Long id) {
        Driver driver = new Driver();
        driver.setId(id);
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
}
