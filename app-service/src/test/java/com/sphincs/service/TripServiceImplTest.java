package com.sphincs.service;

import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(locations = {"classpath:/spring-service-test.xml"})
public class TripServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TripService tripService;

    @Autowired
    private DriverService driverService;

    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    private Driver driver;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeClass
    public void setUp() {
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        driver = new Driver(null, "David", 25, categories, Car.FORD, "4444-ag1");
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
    public void getTripByIdTest() {
        Trip trip = tripService.getTripById(0L);
        Assert.assertEquals(trip.getEndPoint(), "minsk");
        Assert.assertEquals(formatter.format(trip.getEndDate()), "2016-06-30");
    }

    @Test
    public void getTripsByDriverTest() {
        List<Trip> trips = tripService.getTripsByDriver("Bobby");
        Assert.assertEquals(210, trips.get(0).getSumFuel(), 0.1);
        Assert.assertEquals("2016-06-30", formatter.format(trips.get(0).getStartDate()));
    }

    @Test
    public void getTripsByRouteTest() {
        List<Trip> trips = tripService.getTripsByRoute("gomel", "rome");
        Assert.assertEquals(2530d, trips.get(0).getDistance());
        Assert.assertEquals("Spencer", trips.get(0).getDriver().getName());
    }

    @Test
    public void getTripsByDateTest() throws ParseException {
        List<Trip> trips = tripService.getTripsByDate(formatter.parse("2016-07-01"), formatter.parse("2016-07-03"));
        Assert.assertEquals(2530d, trips.get(0).getDistance());
        Assert.assertEquals("Spencer", trips.get(0).getDriver().getName());
    }

    @Test(priority = 1)
    public void updateTripTest() throws ParseException {
        Trip trip = new Trip(1L, driver, "london", "birminghem", 200D, formatter.parse("2016-06-15"), formatter.parse("2016-06-16"));
        tripService.updateTrip(trip);
        trip = tripService.getTripById(1L);
        Assert.assertEquals(trip.getDistance(), 200d);
        Assert.assertEquals(trip.getDriver().getAge(), (Object) 25);
    }

    @Test
    public void addTripTest() throws ParseException {
        List<Trip> trips = tripService.getAllTrips();
        int sizeBefore = trips.size();
        Assert.assertEquals(sizeBefore, 3);
        Trip trip = new Trip(null, driver, "london", "birminghem", 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16"));
        tripService.addTrip(trip);
        trips = tripService.getAllTrips();
        int sizeAfter = trips.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDriverTest() throws ParseException {
        tripService.addTrip(new Trip(null, null, "london", "birminghem", 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartPointTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver, null, "birminghem", 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndPointTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver, "london", null, 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDistanceTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver, "london", "birminghem", null, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartDateTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver, "london", "birminghem", 200d, null, formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndDateTest() throws ParseException {
        tripService.addTrip(new Trip(null, driver, "london", "birminghem", 200d, formatter.parse("2016-06-15"), null));
    }







}
