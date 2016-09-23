package com.sphincs.dao;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.assertj.core.util.Lists;
import org.junit.Assert;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DaoTests {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private TripRepository tripRepository;

    @Before
    public void setUpDatabase() throws ParseException {
        if (driverRepository.count() == 0) {
            fillDrivers();
            fillTrips();
        }
    }

    private void fillDrivers() {
        driverRepository.save(new Driver("Mike", 35));
        driverRepository.save(new Driver("Bobby", 42));
        driverRepository.save(new Driver("Spencer", 50));
        driverRepository.save(new Driver("Misha", 36));
        driverRepository.save(new Driver("Rob", 36));
        driverRepository.save(new Driver("Boris", 37));
        driverRepository.save(new Driver("Mitchel", 28));
    }

    private void fillTrips() throws ParseException {
        tripRepository.save(new Trip("Mike", "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")));
        tripRepository.save(new Trip("Bobby", "FORD", 7.5, "Moscow", "Astana", "2800", Date.valueOf("2016-06-30"), Date.valueOf("2016-07-02")));
        tripRepository.save(new Trip("Misha", "BMW", 7.2, "Gomel", "Rome", "2530", Date.valueOf("2016-07-01"), Date.valueOf("2016-07-03")));
        tripRepository.save(new Trip("Boris", "LADA", 5.5, "Chicago", "Dallas", "3000", Date.valueOf("2016-07-04"), Date.valueOf("2016-07-06")));
    }


    @Test
    public void updateDriverTest() {
        Driver driver = driverRepository.findOne(1L);
        driver.setAge(20);
        driverRepository.save(driver);
        Assert.assertEquals(20, Lists.newArrayList(driverRepository.findAll()).get(0).getAge());
    }


    @Test
    public void getDriverByIdTest() {
        Assert.assertEquals("Mike", driverRepository.findOne(1L).getName());
    }

    @Test
    public void getDriverByNameTest() {
        Assert.assertEquals(36, driverRepository.findByName("Rob").getAge());
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
    public void getTripByIdTest() {
        Assert.assertEquals("Brest", tripRepository.findOne(1L).getStartPoint());
    }

    @Test
    public void getAllTripsTest() {
        Assert.assertEquals(4, Lists.newArrayList(tripRepository.findAll()).size());
    }

    @Test
    public void zzAddTripTest() throws ParseException {
        int size_before = Lists.newArrayList(tripRepository.findAll()).size();
        Trip trip = new Trip("Rob", "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-08-20"), Date.valueOf("2016-08-21"));
        tripRepository.save(trip);
        Assert.assertEquals(size_before + 1, Lists.newArrayList(tripRepository.findAll()).size());
    }

    @Test
    public void getTripByDriverNameTest() {
        Assert.assertEquals("BMW", Lists.newArrayList(tripRepository.findByDriverName("Misha")).get(0).getCar());
    }

    @Test
    public void getTripByCarTest() {
        Assert.assertEquals("Boris", Lists.newArrayList(tripRepository.findByCar("LADA")).get(0).getDriverName());
    }

    @Test
    public void getTripByStartPointAndEndPointTest() {
        Assert.assertEquals("Mike", Lists.newArrayList(tripRepository.findByStartPointAndEndPoint("Brest", "Minsk")).get(0).getDriverName());
    }

    @Test
    public void getTripByStartDateAndEndDateTest() throws ParseException {
        Assert.assertEquals("Mike", Lists.newArrayList(tripRepository.findByStartDateBetweenAndEndDateBetween(
                Date.valueOf("2016-06-30"),
                Date.valueOf("2016-06-30"),
                Date.valueOf("2016-06-30"),
                Date.valueOf("2016-06-30"))).get(0).getDriverName());
    }

    @Test
    public void zzRemoveTripTest() {
        int size_before = Lists.newArrayList(tripRepository.findAll()).size();
        tripRepository.delete(5L);
        Assert.assertEquals(size_before - 1, Lists.newArrayList(tripRepository.findAll()).size());
    }

}
