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

        driver = new Driver(id, name, age);
        Assert.assertEquals(id, driver.getId());
        Assert.assertEquals(name, driver.getName());
        Assert.assertEquals(age, driver.getAge());
    }

    @Test
    public void DriverEqualsTest() {

        driver = new Driver();

        Driver driver1 = new Driver(1L, "Vasili Ivanych", 48);
        Driver driver2 = new Driver(1L, "Vasili Ivanych", 48);
        Driver driver3 = new Driver(1L, "Vasili Ivanych", 20);

        Assert.assertEquals(driver, driver);
        Assert.assertEquals(driver1, driver2);

        Assert.assertFalse(driver.equals(driver1));
        Assert.assertFalse(driver3.equals(driver2));
        Assert.assertFalse(driver1.equals(driver3));
    }

}
