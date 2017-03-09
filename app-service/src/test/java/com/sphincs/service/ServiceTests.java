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
        if (driverService.count() == 0) {
            fillDrivers();
            fillTrips();
        }
    }

    private void fillDrivers() {
        driver1 = driverService.save(new Driver("Mike", 35));
        driver2 = driverService.save(new Driver("Bobby", 42));
        driver3 = driverService.save(new Driver("Misha", 36));
        driver4 = driverService.save(new Driver("Boris", 37));
        driver5 = driverService.save(new Driver("Spencer", 50));
        driver6 = driverService.save(new Driver("Rob", 36));
        driver7 = driverService.save(new Driver("Mitchel", 28));
    }

    private void fillTrips() throws ParseException {
        trip1 = tripService.save(new Trip(driver1, "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")));
        trip2 = tripService.save(new Trip(driver2, "FORD", 7.5, "Moscow", "Astana", "2800", Date.valueOf("2016-06-30"), Date.valueOf("2016-07-02")));
        trip3 = tripService.save(new Trip(driver3, "BMW", 7.2, "Gomel", "Rome", "2530", Date.valueOf("2016-07-01"), Date.valueOf("2016-07-03")));
        trip4 = tripService.save(new Trip(driver4, "LADA", 5.5, "Chicago", "Dallas", "3000", Date.valueOf("2016-07-04"), Date.valueOf("2016-07-06")));
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
        int size_before = Lists.newArrayList(tripService.findAllTrips()).size();
        Trip trip = new Trip(driver6, "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-08-20"), Date.valueOf("2016-08-21"));
        tripService.save(trip);
        Assert.assertEquals(size_before + 1, tripService.findAllTrips().size());
    }

    @Test
    public void fGetTripByIdTest() {
        Assert.assertEquals("Moscow", tripService.findTripById(2L).getStartPoint());
    }

    @Test
    public void gGetAllTripsTest() {
        Assert.assertEquals(5, tripService.findAllTrips().size());
    }

    @Test
    public void hGetTripsByDriverTest() {
        Assert.assertEquals("BMW", tripService.findByDriver(driver3).get(0).getCar());
    }

    @Test
    public void iGetTripsByCarTest() {
        Assert.assertEquals("Boris", tripService.findByCar("LADA").get(0).getDriver().getName());
    }

    @Test
    public void jGetTripsByStartPointAndEndPointTest() {
        Assert.assertEquals("Mike", tripService.findByStartPointAndEndPoint("Brest", "Minsk").get(0).getDriver().getName());
    }

    @Test
    public void kGetTripsByStartDateAndEndDateTest() throws ParseException {
        Assert.assertEquals("Mike",tripService.findByStartDateAndEndDate(
                Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")).get(0).getDriver().getName());
    }
//    @Test
//    public void lUpdateTripTest() {
//        Trip trip = tripService.findTripById(1L);
//        Driver driver = driverService.findDriverById(2L);
////        trip.setDriver(driver2);
//
//        trip.setDriver(driver);
//        trip.setEndPoint("Moscow");
//        driver.addTrip(trip);
//        driverService.save(driver);
//        tripService.save(trip);
//        Assert.assertEquals("Moscow", tripService.findTripById(1L).getEndPoint());
//    }



    @Test
    public void mRemoveTripTest() {
        int size_before = tripService.findAllTrips().size();
        tripService.delete(5L);
        Assert.assertEquals(size_before - 1, tripService.findAllTrips().size());
    }

}