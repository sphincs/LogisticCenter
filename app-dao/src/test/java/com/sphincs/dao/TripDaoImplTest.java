package com.sphincs.dao;

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


@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
public class TripDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TripDao tripDao;
    @Autowired
    private DriverDao driverDao;

    private Driver driver;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeClass
    public void setUp() {
        driver = new Driver(null, "David", 25);
        driverDao.addDriver(driver);
        driver = driverDao.getDriverByName("David");
    }

    @Test
    public void getAllTripsTest() {
        List<Trip> trips = tripDao.getAllTrips();
        Assert.assertNotNull(trips);
        Assert.assertFalse(trips.isEmpty());
    }

    @Test(priority = 2)
    public void removeTripTest() throws ParseException {
        List<Trip> trips = tripDao.getAllTrips();
        int sizeBefore = trips.size();
        tripDao.removeTrip(1L);
        trips = tripDao.getAllTrips();
        Assert.assertEquals(sizeBefore, trips.size() + 1);
    }

    @Test
    public void getTripByIdTest() {
        Trip trip = tripDao.getTripById(0L);
        Assert.assertEquals(trip.getEndPoint(), "Minsk");
        Assert.assertEquals(formatter.format(trip.getEndDate()), "2016-06-30");
    }

    @Test
    public void getTripsByDriverTest() {
        List<Trip> trips = tripDao.getTripsByDriver("Bobby");
        Assert.assertEquals("210,00", trips.get(0).getSumFuel());
        Assert.assertEquals("2016-06-30", formatter.format(trips.get(0).getStartDate()));
    }

    @Test
    public void getTripsByCarTest() {
        List<Trip> trips = tripDao.getTripsByCar("FORD");
        Assert.assertEquals(trips.size(), 1);
        Assert.assertEquals(trips.get(0).getDriverName(), "Bobby");
    }

    @Test
    public void getTripsByRouteTest() {
        List<Trip> trips = tripDao.getTripsByRoute("Gomel", "Rome");
        Assert.assertEquals("2530", trips.get(0).getDistance());
        Assert.assertEquals("Misha", trips.get(0).getDriverName());
    }

    @Test
    public void getTripsByDateTest() throws ParseException {
        List<Trip> trips = tripDao.getTripsByDate(formatter.parse("2016-07-03"), formatter.parse("2016-07-08"));
        Assert.assertEquals("3000", trips.get(0).getDistance());
        Assert.assertEquals("Boris", trips.get(0).getDriverName());
    }

    @Test(priority = 1)
    public void updateTripTest() throws ParseException {
        Trip trip = tripDao.getTripById(1L);
        trip.setDriverName("Boris");
        tripDao.updateTrip(trip);
        trip = tripDao.getTripById(1L);
        Assert.assertEquals(trip.getDistance(), "2800");
        Assert.assertEquals(trip.getSumFuel(), "210,00");
        Assert.assertEquals(trip.getDriverName(), "Boris");
    }

    @Test
    public void addTripTest() throws ParseException {
        List<Trip> trips = tripDao.getAllTrips();
        int sizeBefore = trips.size();
        Trip trip = new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16"));
        tripDao.addTrip(trip);
        trips = tripDao.getAllTrips();
        int sizeAfter = trips.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDriverTest() throws ParseException {
        tripDao.addTrip(new Trip(null, null, "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyDriverTest() throws ParseException {
        tripDao.addTrip(new Trip(null, "", "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullCarTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), null, 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyCarTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = {IllegalArgumentException.class, NullPointerException.class})
    public void addTripWithNullFuelRateTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", null, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithZeroFuelRateTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 0d, "London", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartPointTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, null, "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyStartPointTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "", "Birminghem", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndPointTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", null, "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyEndPointTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "", "200", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullDistanceTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", null, formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithEmptyDistanceTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "", formatter.parse("2016-06-15"), formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullStartDateTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "200", null, formatter.parse("2016-06-16")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTripWithNullEndDateTest() throws ParseException {
        tripDao.addTrip(new Trip(null, driver.getName(), "LADA", 5.5, "London", "Birminghem", "200", formatter.parse("2016-06-15"), null));
    }

}
