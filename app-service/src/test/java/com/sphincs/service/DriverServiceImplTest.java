package com.sphincs.service;

import com.sphincs.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration(locations = {"classpath:/spring-service-test.xml"})
public class DriverServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DriverService driverService;

    @Test
    public void addDriverTest() {
        List<Driver> drivers = driverService.getAllDrivers();
        int sizeBefore = drivers.size();
        Driver driver = new Driver(null, "Rick", 25);
        driverService.addDriver(driver);
        drivers = driverService.getAllDrivers();
        int sizeAfter = drivers.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullNameTest() {
        driverService.addDriver(new Driver(null, null, 25));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithEmptyNameTest() {
        driverService.addDriver(new Driver(null, "", 25));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullAgeTest() {
        driverService.addDriver(new Driver(null, "Rick", null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithIncorrectAgeTest() {
        driverService.addDriver(new Driver(null, "Rick", 15));
    }

    @Test(priority = 1)
    public void getAllDriversTest() {
        List<Driver> drivers = driverService.getAllDrivers();
        Assert.assertNotNull(drivers);
        Assert.assertFalse(drivers.isEmpty());
    }

    @Test(priority = 2)
    public void removeDriverWithTripsTest() {
        List<Driver> drivers = driverService.getAllDrivers();
        int sizeBefore = drivers.size();
        driverService.removeDriver(3L);
        drivers = driverService.getAllDrivers();
        Assert.assertEquals(sizeBefore, drivers.size() + 1);
    }

    @Test(priority = 3)
    public void removeDriverWithoutTripsTest() {
        List<Driver> drivers = driverService.getAllDrivers();
        int sizeBefore = drivers.size();
        driverService.removeDriver(4L);
        drivers = driverService.getAllDrivers();
        Assert.assertEquals(sizeBefore, drivers.size() + 1);
    }

    @Test
    public void getDriverByIdTest() {
        Driver driver = driverService.getDriverById(0L);
        Assert.assertEquals(driver.getAge(), (Object) 35);
        Assert.assertEquals(driver.getName(), "Mike");
    }

    @Test
    public void getDriverByNameTest() {
        Driver driver = driverService.getDriverByName("Spencer");
        Assert.assertEquals(driver.getAge(), (Object) 50);
        Assert.assertEquals(driver.getId(), (Object) 2L);
    }

    @Test(priority = 1)
    public void updateDriverTest() {
        Driver driver = new Driver(0L, "Rick", 25);
        driverService.updateDriver(driver);
        driver = driverService.getDriverById(0L);
        Assert.assertEquals(driver.getName(), "Rick");
        Assert.assertEquals(driver.getAge(), (Object) 25);
    }

}
