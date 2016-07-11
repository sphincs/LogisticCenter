package com.sphincs.service;

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


@ContextConfiguration(locations = {"classpath:/spring-service-test.xml"})
public class DriverServiceImplTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private DriverService driverService;

    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    Set<Category> categories = new HashSet<>();

    @BeforeClass
    public void init() {
        categories.add(Category.B);
    }

    @Test
    public void addDriverTest() {
        List<Driver> drivers = driverService.getAllDrivers();
        int sizeBefore = drivers.size();
        Driver driver = new Driver(null, "Rick", 25, categories, Car.FORD, "4444-ag1");
        driverService.addDriver(driver);
        drivers = driverService.getAllDrivers();
        int sizeAfter = drivers.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullNameTest(){
        driverService.addDriver(new Driver(null, null, 25, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullAgeTest(){
        driverService.addDriver(new Driver(null, "Rick", null, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithIncorrectAgeTest(){
        driverService.addDriver(new Driver(null, "Rick", 15, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullCategoriesTest(){
        driverService.addDriver(new Driver(null, "Rick", 25, null, Car.FORD, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullCarTest(){
        driverService.addDriver(new Driver(null, "Rick", 25, categories, null, "4444-ag1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addDriverWithNullNumberTest(){
        driverService.addDriver(new Driver(null, "Rick", 25, categories, Car.FORD, null));
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
        Assert.assertEquals(driver.getCar(), Car.PEUGEOT);
    }

    @Test
    public void getDriverByNameTest() {
        Driver driver = driverService.getDriverByName("Spencer");
        Assert.assertEquals(driver.getAge(), (Object) 50);
        Assert.assertEquals(driver.getCar(), Car.DAF);
    }

    @Test
    public void getDriversByCarTest() {
        List<Driver> drivers = driverService.getDriversByCar(Car.BMW);
        Assert.assertEquals(drivers.size(), 2);
        Assert.assertEquals(drivers.get(0).getName(), "Bobby");
        Assert.assertEquals(drivers.get(1).getName(), "Misha");
    }

    @Test(priority = 1)
    public void updateDriverTest() {
        Driver driver = new Driver(0L, "Rick", 25, categories, Car.FORD, "4444-ag1");
        driverService.updateDriver(driver);
        driver = driverService.getDriverById(0L);
        Assert.assertEquals(driver.getName(), "Rick");
        Assert.assertEquals(driver.getCarNumber(), "4444-ag1");
    }





}
