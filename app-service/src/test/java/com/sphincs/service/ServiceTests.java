package com.sphincs.service;

import com.google.common.collect.Lists;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceTests {

    @Autowired
    private DriverService driverService;

    @Autowired
    private TripService tripService;

    @Before
    public void setUpDatabase() throws ParseException {
        if (driverService.count() == 0) {
            fillDrivers();
            fillTrips();
        }
    }

    private void fillDrivers() {
        driverService.save(new Driver("Mike", 35));
        driverService.save(new Driver("Bobby", 42));
        driverService.save(new Driver("Spencer", 50));
        driverService.save(new Driver("Misha", 36));
        driverService.save(new Driver("Rob", 36));
        driverService.save(new Driver("Boris", 37));
        driverService.save(new Driver("Mitchel", 28));
    }

    private void fillTrips() throws ParseException {
        tripService.save(new Trip("Mike", "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")));
        tripService.save(new Trip("Bobby", "FORD", 7.5, "Moscow", "Astana", "2800", Date.valueOf("2016-06-30"), Date.valueOf("2016-07-02")));
        tripService.save(new Trip("Misha", "BMW", 7.2, "Gomel", "Rome", "2530", Date.valueOf("2016-07-01"), Date.valueOf("2016-07-03")));
        tripService.save(new Trip("Boris", "LADA", 5.5, "Chicago", "Dallas", "3000", Date.valueOf("2016-07-04"), Date.valueOf("2016-07-06")));
    }

    @Test
    public void aAddDriverTest() {
        long size_before = driverService.count();
        driverService.save(new Driver("Serg", 27));
        Assert.assertEquals(size_before + 1, driverService.count());
    }

    @Test
    public void bGetDriverByNameTest() {
        Assert.assertEquals(36, driverService.findByName("Rob").getAge());
    }

    @Test
    public void cUpdateDriverTest() {
        Driver driver = driverService.findByName("Boris");
        driver.setAge(50);
        driverService.save(driver);
        Assert.assertEquals(50, driverService.findByName("Boris").getAge());
    }

    @Test
    public void dDeleteDriverTest() {
        long size_before = driverService.count();
        driverService.delete(7L);
        Assert.assertEquals(size_before - 1, driverService.count());
    }

    @Test
    public void eAddTripTest() throws ParseException {
        int size_before = Lists.newArrayList(tripService.findAll()).size();
        Trip trip = new Trip("Rob", "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-08-20"), Date.valueOf("2016-08-21"));
        tripService.save(trip);
        Assert.assertEquals(size_before + 1, tripService.findAll().size());
    }

    @Test
    public void fGetTripByIdTest() {
        Assert.assertEquals("Moscow", tripService.findById(2L).getStartPoint());
    }

    @Test
    public void gGetAllTripsTest() {
        Assert.assertEquals(5, tripService.findAll().size());
    }

    @Test
    public void hGetTripsByDriverNameTest() {
        Assert.assertEquals("BMW", tripService.findByDriverName("Misha").get(0).getCar());
    }

    @Test
    public void iUpdateTripTest() {
        Trip trip = tripService.findById(1L);
        trip.setDriverName("Steven");
        trip.setEndPoint("Moscow");
        tripService.save(trip);
        Assert.assertEquals("Moscow", tripService.findByDriverName("Steven").get(0).getEndPoint());
    }

    @Test
    public void jGetTripsByCarTest() {
        Assert.assertEquals("Boris", tripService.findByCar("LADA").get(0).getDriverName());
    }

    @Test
    public void kGetTripsByStartPointAndEndPointTest() {
        Assert.assertEquals("Steven", tripService.findByStartPointAndEndPoint("Brest", "Moscow").get(0).getDriverName());
        Assert.assertTrue(tripService.findByStartPointAndEndPoint("Brest", "Karaganda").isEmpty());
    }

    @Test
    public void lGetTripsByStartDateAndEndDateTest() throws ParseException {
        Assert.assertEquals("Steven",tripService.findByStartDateAndEndDate(
                Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")).get(0).getDriverName());
    }

    @Test
    public void mRemoveTripTest() {
        int size_before = tripService.findAll().size();
        tripService.delete(5L);
        Assert.assertEquals(size_before - 1, tripService.findAll().size());
    }

}