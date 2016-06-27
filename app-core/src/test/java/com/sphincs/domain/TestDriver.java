package com.sphincs.domain;

import org.junit.Assert;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

public class TestDriver extends Assert {

    Driver driver;

    @Test
    public void TestDriverFields() {
        Long id = 4L;
        String name = "Vasili Ivanych";
        Integer age = 48;
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        categories.add(Category.C);
        Car car = Car.DAF;
        String number = "1707AB1";
        Double fuelRate100 = 10.0;

        driver = new Driver(id, name, age, categories, car, number, fuelRate100);
        assertEquals(id, driver.getId());
        assertEquals(name, driver.getName());
        assertEquals(age, driver.getAge());
        assertEquals(categories.toString(), driver.getCategories().toString());
        assertEquals(car, driver.getCar());
        assertEquals(number, driver.getNumber());
        assertEquals(fuelRate100, driver.getFuelRate100());
    }

    @Test
    public void TetsDriverEquals() {

        driver = new Driver();
        Set<Category> categories1 = new HashSet<>();
        categories1.add(Category.B);
        categories1.add(Category.C);

        Set<Category> categories2 = new HashSet<>();
        categories2.add(Category.B);

        Driver driver1 = new Driver(1L, "Vasili Ivanych", 48, categories1, Car.FORD, "0013ih1", 6.2);
        Driver driver2 = new Driver(1L, "Vasili Ivanych", 48, categories1, Car.FORD, "0013ih1", 6.2);
        Driver driver3 = new Driver(1L, "Vasili Ivanych", 48, categories2, Car.FORD, "0013ih1", 6.2);

        assertEquals(driver, driver);
        assertEquals(driver1, driver2);

        assertFalse(driver.equals(driver1));
        assertFalse(driver3.equals(driver2));
        assertFalse(driver1.equals(driver3));
    }


}
