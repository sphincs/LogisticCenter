package com.sphincs.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class DriverTest {

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
        String number = "1707-AB1";
        Double fuelRate100 = car.getFuelRate();

        driver = new Driver(id, name, age, categories, car, number);
        Assert.assertEquals(id, driver.getId());
        Assert.assertEquals(name, driver.getName());
        Assert.assertEquals(age, driver.getAge());
        Assert.assertEquals(categories.toString(), driver.getCategories().toString());
        Assert.assertEquals(car, driver.getCar());
        Assert.assertEquals(number, driver.getCarNumber());
        Assert.assertEquals(fuelRate100, driver.getFuelRate100());
    }

    @Test
    public void TetsDriverEquals() {

        driver = new Driver();
        Set<Category> categories1 = new HashSet<>();
        categories1.add(Category.B);
        categories1.add(Category.C);

        Set<Category> categories2 = new HashSet<>();
        categories2.add(Category.B);

        Driver driver1 = new Driver(1L, "Vasili Ivanych", 48, categories1, Car.FORD, "0013-ih1");
        Driver driver2 = new Driver(1L, "Vasili Ivanych", 48, categories1, Car.FORD, "0013-ih1");
        Driver driver3 = new Driver(1L, "Vasili Ivanych", 48, categories2, Car.FORD, "0013-ih1");

        Assert.assertEquals(driver, driver);
        Assert.assertEquals(driver1, driver2);

        Assert.assertFalse(driver.equals(driver1));
        Assert.assertFalse(driver3.equals(driver2));
        Assert.assertFalse(driver1.equals(driver3));
    }


}
