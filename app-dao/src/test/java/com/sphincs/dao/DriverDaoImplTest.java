package com.sphincs.dao;


import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
import com.sphincs.domain.Driver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
public class DriverDaoImplTest extends Assert{

    @Autowired
    private DriverDao driverDao;

    Set<Category> categories = new HashSet<>();

    @Before
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
        assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverWithNullNameTest(){
        driverDao.addDriver(new Driver(null, null, 25, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverWithNullAgeTest(){
        driverDao.addDriver(new Driver(null, "Rick", null, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverWithIncorrectAgeTest(){
        driverDao.addDriver(new Driver(null, "Rick", 15, categories, Car.FORD, "4444-ag1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverWithNullCategoriesTest(){
        driverDao.addDriver(new Driver(null, "Rick", 25, null, Car.FORD, "4444-ag1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverWithNullCarTest(){
        driverDao.addDriver(new Driver(null, "Rick", 25, categories, null, "4444-ag1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverWithNullNumberTest(){
        driverDao.addDriver(new Driver(null, "Rick", 25, categories, Car.FORD, null));
    }

    @Test
    public void getAllDriversTest() {
        List<Driver> drivers = driverDao.getAllDrivers();
        assertNotNull(drivers);
        assertFalse(drivers.isEmpty());
    }

    @Test
    public void getDriverByIdTest() {
        Driver driver = driverDao.getDriverById(1L);
        assertEquals(driver.getCar(), Car.BMW);
        assertEquals(driver.getName(), "Bobby");
    }

    @Test
    public void getDriverByNameTest() {
        Driver driver = driverDao.getDriverByName("Spencer");
        assertEquals(driver.getCar(), Car.DAF);
        assertEquals(driver.getAge(), (Object) 50);
    }

    @Test
    public void getDriversByCarTest() {
        List<Driver> driver = driverDao.getDriversByCar(Car.BMW);
        assertEquals(driver.size(), 1);
        assertEquals(driver.get(0).getAge(), (Object) 42);
    }

    @Test
    public void removeDriverTest() {
        List<Driver> drivers = driverDao.getAllDrivers();
        int sizeBefore = drivers.size();
        driverDao.removeDriver(0L);
        drivers = driverDao.getAllDrivers();
        assertEquals(sizeBefore, drivers.size() + 1);
    }

    @Test
    public void updateDriverTest() {
        Driver driver = new Driver(0L, "Rick", 25, categories, Car.FORD, "4444-ag1");
        driverDao.updateDriver(driver);
        driver = driverDao.getDriverById(0L);
        assertEquals(driver.getName(), "Rick");
    }

}
