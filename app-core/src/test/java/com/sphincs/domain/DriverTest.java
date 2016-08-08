package com.sphincs.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DriverTest {

    Driver driver;

    @Test
    public void DriverFieldsTest() {
        Long id = 4L;
        String name = "Vasili Ivanych";
        Integer age = 48;
        String car = "DAF";
        String number = "1111-AB1";
        Double fuelRate100 = 14.5;

        driver = new Driver(id, name, age, car, number, fuelRate100);
        Assert.assertEquals(id, driver.getId());
        Assert.assertEquals(name, driver.getName());
        Assert.assertEquals(age, driver.getAge());
        Assert.assertEquals(car, driver.getCar());
        Assert.assertEquals(number, driver.getCarNumber());
        Assert.assertEquals(fuelRate100, driver.getFuelRate100());
    }

    @Test
    public void DriverEqualsTest() {

        driver = new Driver();

        Driver driver1 = new Driver(1L, "Vasili Ivanych", 48, "FORD", "2222-AB2", 7.0);
        Driver driver2 = new Driver(1L, "Vasili Ivanych", 48, "FORD", "2222-AB2", 7.0);
        Driver driver3 = new Driver(1L, "Vasili Ivanych", 48, "FORD", "3333-AB2", 7.0);

        Assert.assertEquals(driver, driver);
        Assert.assertEquals(driver1, driver2);

        Assert.assertFalse(driver.equals(driver1));
        Assert.assertFalse(driver3.equals(driver2));
        Assert.assertFalse(driver1.equals(driver3));
    }
}
