package com.sphincs.dao;

import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.testng.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
public class TripDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TripDao tripDao;
    @Autowired
    private DriverDao driverDao;
    private Driver driver;


    @BeforeClass
    public void setUp() throws Exception {
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        driver = new Driver(null, "Rick", 25, categories, Car.FORD, "4444-ag1");
        driverDao.addDriver(driver);
        driver = driverDao.getDriverByName("Rick");
    }

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void getAllTrips() {
        List<Trip> trips = tripDao.getAllTrips();
        Assert.assertNotNull(trips);
        Assert.assertFalse(trips.isEmpty());
    }

    @Test
    public void removeTrip() throws ParseException {
        List<Trip> trips = tripDao.getAllTrips();
        int sizeBefore = trips.size();
        tripDao.removeTrip(0L);
        trips = tripDao.getAllTrips();
        Assert.assertEquals(sizeBefore, trips.size() + 1);
    }

    @Test
    public void getTripById() {
        Trip trip = tripDao.getTripById(0L);
        Assert.assertEquals(trip.getEndPoint(), "minsk");
        Assert.assertEquals(formatter.format(trip.getEndDate()), "2016-06-30");
    }

    @Test
    public void getTripsByDriver() {
        List<Trip> trips = tripDao.getTripsByDriver("Bobby");
        Assert.assertEquals(210, trips.get(0).getSumFuel(), 0.1);
        Assert.assertEquals("2016-06-30", formatter.format(trips.get(0).getStartDate()));
    }

    @Test
    public void getTripsByRoute() {
        List<Trip> trips = tripDao.getTripsByRoute("gomel", "rome");
        Assert.assertEquals(2530d, trips.get(0).getDistance());
        Assert.assertEquals("Spencer", trips.get(0).getDriver().getName());
    }

    @Test
    public void getTripsByDate() throws ParseException {
        List<Trip> trips = tripDao.getTripsByDate(formatter.parse("2016-07-01"), formatter.parse("2016-07-03"));
        Assert.assertEquals(2530d, trips.get(0).getDistance());
        Assert.assertEquals("Spencer", trips.get(0).getDriver().getName());
    }

    @Test
    public void updateTrip() throws ParseException {
        Trip trip = new Trip(1L, driver, "london", "birminghem", 200D, formatter.parse("2016-06-15"), formatter.parse("2016-06-16"));
        tripDao.updateTrip(trip);
        trip = tripDao.getTripById(1L);
        Assert.assertEquals(trip.getDistance(), 200d);
        Assert.assertEquals(trip.getDriver().getAge(), (Object) 25);
    }

    @Test
    public void addTrip() throws ParseException {
        List<Trip> trips = tripDao.getAllTrips();
        int sizeBefore = trips.size();
        Trip trip = new Trip(null, driver, "london", "birminghem", 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16"));
        tripDao.addTrip(trip);
        trips = tripDao.getAllTrips();
        int sizeAfter = trips.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDriverTest() throws ParseException {
        tripDao.addTrip(new Trip(null, null, "london", "birminghem", 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartPointTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver, null, "birminghem", 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndPointTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver, "london", null, 200d, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDistanceTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver, "london", "birminghem", null, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartDateTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver, "london", "birminghem", 200d, null, formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndDateTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver, "london", "birminghem", 200d, formatter.parse("2016-06-15"), null));
    }


}
