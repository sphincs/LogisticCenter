package com.sphincs.service;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ContextConfiguration(locations = {"classpath:/spring-service-test.xml"})
public class TripServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TripService tripService;

    @Autowired
    private DriverService driverService;

    private Driver driver;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeClass
    public void setUp() {
        driver = new Driver(null, "David", 25);
        driverService.addDriver(driver);
        driver = driverService.getDriverByName("David");
    }

    @Test
    public void getAllTripsTest() {
        List<Trip> trips = tripService.getAllTrips();
        Assert.assertNotNull(trips);
        Assert.assertFalse(trips.isEmpty());
    }

    @Test(priority = 2)
    public void removeTripTest() throws ParseException {
        List<Trip> trips = tripService.getAllTrips();
        int sizeBefore = trips.size();
        tripService.removeTrip(1L);
        trips = tripService.getAllTrips();
        Assert.assertEquals(sizeBefore, trips.size() + 1);
    }

    @Test
    public void addTripTest() throws ParseException {
        List<Trip> trips = tripService.getAllTrips();
        int sizeBefore = trips.size();
        Assert.assertEquals(sizeBefore, 4);
        Trip trip = new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16"));
        tripService.addTrip(trip);
        trips = tripService.getAllTrips();
        int sizeAfter = trips.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDriverTest() throws ParseException {
        tripService.addTrip(new Trip(null, null, "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyDriverTest() throws ParseException {
        tripService.addTrip(new Trip(null, "", "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullCarTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), null, 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyCarTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = {IllegalArgumentException.class, NullPointerException.class})
    public void addTripWithNullFuelRateTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", null, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithZeroFuelRateTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 0d, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartPointTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, null, "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyStartPointTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndPointTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", null, "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyEndPointTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDistanceTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", null, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyDistanceTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartDateTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "200", null, formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndDateTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), null));
    }

    @Test
    public void getTripByIdTest() {
        Trip trip = tripService.getTripById(0L);
        Assert.assertEquals(trip.getEndPoint(), "Minsk");
        Assert.assertEquals(formatter.format(trip.getEndDate()), "2016-06-30");
    }

    @Test
    public void getTripsByDriverTest() {
        List<Trip> trips = tripService.getTripsByDriver("Bobby");
        Assert.assertEquals("210,00", trips.get(0).getSumFuel());
        Assert.assertEquals("2016-06-30", formatter.format(trips.get(0).getStartDate()));
    }

    @Test
    public void getTripsByCarTest() {
        List<Trip> trips = tripService.getTripsByCar("BMW");
        Assert.assertEquals(trips.size(), 1);
        Assert.assertEquals(trips.get(0).getDriverName(), "Misha");
    }

    @Test
    public void getTripsByRouteTest() {
        List<Trip> trips = tripService.getTripsByRoute("Gomel", "Rome");
        Assert.assertEquals("2530", trips.get(0).getDistance());
        Assert.assertEquals("Misha", trips.get(0).getDriverName());
    }

    @Test
    public void getTripsByDateTest() throws ParseException {
        List<Trip> trips = tripService.getTripsByDate(formatter.parse("2016-07-01"), formatter.parse("2016-07-06"));
        Assert.assertEquals(trips.size(), 2);
    }

    @Test(priority = 1)
    public void updateTripTest() throws ParseException {
        Trip trip = new Trip(1L, driver.getName(), "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16"));
        tripService.updateTrip(trip);
        trip = tripService.getTripById(1L);
        Assert.assertEquals(trip.getDistance(), "200");
        Assert.assertEquals(trip.getDriverName(), "David");
    }

}
