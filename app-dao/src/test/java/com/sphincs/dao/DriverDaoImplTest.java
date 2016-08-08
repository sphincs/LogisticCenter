package com.sphincs.dao;

import com.sphincs.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
public class DriverDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DriverDao driverDao;

    @Test
    public void addDriverTest() {
        List<Driver> drivers = driverDao.getAllDrivers();
        int sizeBefore = drivers.size();
        Driver driver = new Driver(null, "Rick", 25);
        driverDao.addDriver(driver);
        drivers = driverDao.getAllDrivers();
        int sizeAfter = drivers.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullNameTest() {
        driverDao.addDriver(new Driver(null, null, 25));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullAgeTest() {
        driverDao.addDriver(new Driver(null, "Rick", null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithIncorrectAgeTest() {
        driverDao.addDriver(new Driver(null, "Rick", 15));
    }

    @Test
    public void getAllDriversTest() {
        List<Driver> drivers = driverDao.getAllDrivers();
        Assert.assertNotNull(drivers);
        Assert.assertFalse(drivers.isEmpty());
    }

    @Test
    public void getDriverByIdTest() {
        Driver driver = driverDao.getDriverById(1L);
        Assert.assertEquals(driver.getAge(), (Object) 42);
        Assert.assertEquals(driver.getName(), "Bobby");
    }

    @Test
    public void getDriverByNameTest() {
        Driver driver = driverDao.getDriverByName("Spencer");
        Assert.assertEquals(driver.getId(), (Object) 2L);
        Assert.assertEquals(driver.getAge(), (Object) 50);
    }

    @Test(priority = 2)
    public void removeDriverTest() {
        List<Driver> drivers = driverDao.getAllDrivers();
        int sizeBefore = drivers.size();
        driverDao.removeDriver(4L);
        drivers = driverDao.getAllDrivers();
        Assert.assertEquals(sizeBefore, drivers.size() + 1);
    }

    @Test(priority = 1)
    public void updateDriverTest() {
        Driver driver = new Driver(0L, "Rick", 25);
        driverDao.updateDriver(driver);
        driver = driverDao.getDriverById(0L);
        Assert.assertEquals(driver.getName(), "Rick");
    }

}
