package com.sphincs.dao;


import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
import com.sphincs.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
public class DriverDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DriverDao driverDao;

    Set<Category> categories = new HashSet<>();

    @BeforeClass
    public void init() {
        categories.add(Category.B);
    }

    @Test
    public void addDriverTest() {
        List<Driver> drivers = driverDao.getAllDrivers();
        int sizeBefore = drivers.size();
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        Driver driver = new Driver(null, "Rick", 25, categories, Car.FORD, "4444-ag1");
        driverDao.addDriver(driver);
        drivers = driverDao.getAllDrivers();
        int sizeAfter = drivers.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullNameTest(){
        driverDao.addDriver(new Driver(null, null, 25, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullAgeTest(){
        driverDao.addDriver(new Driver(null, "Rick", null, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithIncorrectAgeTest(){
        driverDao.addDriver(new Driver(null, "Rick", 15, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullCategoriesTest(){
        driverDao.addDriver(new Driver(null, "Rick", 25, null, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullCarTest(){
        driverDao.addDriver(new Driver(null, "Rick", 25, categories, null, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullNumberTest(){
        driverDao.addDriver(new Driver(null, "Rick", 25, categories, Car.FORD, null));
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
        Assert.assertEquals(driver.getCar(), Car.BMW);
        Assert.assertEquals(driver.getName(), "Bobby");
    }

    @Test
    public void getDriverByNameTest() {
        Driver driver = driverDao.getDriverByName("Spencer");
        Assert.assertEquals(driver.getCar(), Car.DAF);
        Assert.assertEquals(driver.getAge(), (Object) 50);
    }

    @Test
    public void getDriversByCarTest() {
        List<Driver> driver = driverDao.getDriversByCar(Car.BMW);
        Assert.assertEquals(driver.size(), 1);
        Assert.assertEquals(driver.get(0).getAge(), (Object) 42);
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
        Driver driver = new Driver(0L, "Rick", 25, categories, Car.FORD, "4444-ag1");
        driverDao.updateDriver(driver);
        driver = driverDao.getDriverById(0L);
        Assert.assertEquals(driver.getName(), "Rick");
    }

}
