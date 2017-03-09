package com.sphincs.dao;

import com.google.common.collect.Lists;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.text.ParseException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DaoTests {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private TripRepository tripRepository;

    private static Driver driver1;
    private static Driver driver2;
    private static Driver driver3;
    private static Driver driver4;
    private static Driver driver5;
    private static Driver driver6;
    private static Driver driver7;
    private static Trip trip1;
    private static Trip trip2;
    private static Trip trip3;
    private static Trip trip4;

    @Before
    public void setUpDatabase() throws ParseException {
        if (driverRepository.count() == 0) {
            fillDrivers();
            fillTrips();
       }
    }

    private void fillDrivers() {
        driver1 = driverRepository.save(new Driver("Mike", 35));
        driver2 = driverRepository.save(new Driver("Bobby", 42));
        driver3 = driverRepository.save(new Driver("Misha", 36));
        driver4 = driverRepository.save(new Driver("Boris", 37));
        driver5 = driverRepository.save(new Driver("Spencer", 50));
        driver6 = driverRepository.save(new Driver("Rob", 36));
        driver7 = driverRepository.save(new Driver("Mitchel", 28));
    }

    private void fillTrips() throws ParseException {
        trip1 = tripRepository.save(new Trip(driver1, "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")));
        trip2 = tripRepository.save(new Trip(driver2, "FORD", 7.5, "Moscow", "Astana", "2800", Date.valueOf("2016-06-30"), Date.valueOf("2016-07-02")));
        trip3 = tripRepository.save(new Trip(driver3, "BMW", 7.2, "Gomel", "Rome", "2530", Date.valueOf("2016-07-01"), Date.valueOf("2016-07-03")));
        trip4 = tripRepository.save(new Trip(driver4, "LADA", 5.5, "Chicago", "Dallas", "3000", Date.valueOf("2016-07-04"), Date.valueOf("2016-07-06")));
    }

    @Test
    public void updateDriverTest() {
        Driver driver = driverRepository.findOne(1L);
        driver.setAge(20);
        driverRepository.save(driver);
        assertEquals(20, Lists.newArrayList(driverRepository.findAll()).get(0).getAge());
    }

    @Test
    public void getDriverByIdTest() {
        assertEquals("Mike", driverRepository.findOne(1L).getName());
    }

    @Test
    public void getDriverByNameTest() {
        assertEquals(36, driverRepository.findByName("Rob").get().getAge());
    }

    @Test(expected = ConstraintViolationException.class)
    public void addDriverWithWrongAgeTest() {
        driverRepository.save(new Driver("Bob", 15));
        driverRepository.save(new Driver("Rob", 70));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addDriverWithWrongNameTest() {
        driverRepository.save(new Driver("Bob Чик", 20));
    }

    @Test(expected = PersistenceException.class)
    public void addDriversWithSameNamesTest() {
        driverRepository.save(new Driver("Bob", 20));
        driverRepository.save(new Driver("Bob", 40));
    }

    @Test
    public void zRemoveDriverTest() {
        int size_before = (Lists.newArrayList(driverRepository.findAll())).size();
        driverRepository.delete(1L);
        int size_after = (Lists.newArrayList(driverRepository.findAll())).size();
        assertEquals(size_before - 1, (Lists.newArrayList(driverRepository.findAll())).size());
    }

    @Test
    public void getTripByIdTest() {
        assertEquals("Brest", tripRepository.findOne(1L).getStartPoint());
    }

    @Test
    public void getAllTripsTest() {
        assertEquals(4, Lists.newArrayList(tripRepository.findAll()).size());
    }

    @Test
    public void zAddTripTest() throws ParseException {
        int size_before = Lists.newArrayList(tripRepository.findAll()).size();
        Driver driver = new Driver("Asas", 50);
        driver = driverRepository.save(driver);
        Trip trip = new Trip(driver, "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-08-20"), Date.valueOf("2016-08-21"));
        tripRepository.save(trip);
        assertEquals(size_before + 1, Lists.newArrayList(tripRepository.findAll()).size());
    }

    @Test
    public void getTripByDriverTest() {
        assertEquals("BMW", tripRepository.findByDriver(driverRepository.findById(3L).get())
                .get(0)
                .getCar());
    }

    @Test
    public void getTripByCarTest() {
        assertEquals("Boris", tripRepository.findByCar("LADA")
                .get(0)
                .getDriver()
                .getName());
    }

    @Test
    public void getTripByStartPointAndEndPointTest() {
        assertEquals("Mike", tripRepository.findByStartPointAndEndPoint("Brest", "Minsk")
                .get(0)
                .getDriver()
                .getName());
    }

    @Test
    public void getTripByStartDateAndEndDateTest() throws ParseException {
        assertEquals("Mike", tripRepository.findByStartDateBetweenAndEndDateBetween(
                Date.valueOf("2016-06-30"),
                Date.valueOf("2016-06-30"),
                Date.valueOf("2016-06-30"),
                Date.valueOf("2016-06-30"))
                .get(0)
                .getDriver()
                .getName());
    }

    @Test
    public void zRemoveTripTest() {
        int size_before = Lists.newArrayList(tripRepository.findAll()).size();
        tripRepository.delete(3L);
        assertEquals(size_before - 1, Lists.newArrayList(tripRepository.findAll()).size());
    }

}
