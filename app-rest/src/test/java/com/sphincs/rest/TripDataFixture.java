package com.sphincs.rest;


import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripDataFixture {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Trip getNewTrip() throws ParseException {
        Trip trip = new Trip();
        trip.setDriver(DriverDataFixture.getExistingDriver(1L));
        trip.setStartPoint("brest");
        trip.setEndPoint("minsk");
        trip.setDistance(350d);
        trip.setStartDate(formatter.parse("2016-07-14"));
        trip.setEndDate(formatter.parse("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static Trip getNewTripWithId(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriver(getDriver(1L));
        trip.setStartPoint("brest");
        trip.setEndPoint("minsk");
        trip.setDistance(350d);
        trip.setStartDate(formatter.parse("2016-07-14"));
        trip.setEndDate(formatter.parse("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static List<Trip> getTripsByDate() throws ParseException {
        List<Trip> trips = new ArrayList<>();
        trips.add(getNewTripWithId(1L));
        trips.add(getNewTripWithId(2L));
        return trips;
    }

    public static Trip getNewTripWithDriverRalph(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriver(DriverDataFixture.getExistingDriverByName("Ralph"));
        trip.setStartPoint("brest");
        trip.setEndPoint("minsk");
        trip.setDistance(350d);
        trip.setStartDate(formatter.parse("2016-07-14"));
        trip.setEndDate(formatter.parse("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static Trip getBadNewTrip() throws ParseException {
        Trip trip = new Trip();
        trip.setDriver(null);
        trip.setStartPoint("brest");
        trip.setEndPoint("minsk");
        trip.setDistance(350d);
        trip.setStartDate(formatter.parse("2016-07-14"));
        trip.setEndDate(formatter.parse("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static List<Trip> getAllTrips() throws ParseException {
        List<Trip> trips = new ArrayList<>();
        trips.add(getNewTripWithId(1L));
        trips.add(getNewTripWithId(2L));
        return trips;
    }

    public static List<Trip> getTripsByDriver(String driverName) throws ParseException {
        List<Trip> trips = new ArrayList<>();
        trips.add(getNewTripWithDriverRalph(1L));
        trips.add(getNewTripWithDriverRalph(2L));
        trips.add(getNewTripWithDriverRalph(3L));
        return trips;
    }

    public static Driver getDriver(Long id) {
        Driver driver = new Driver();
        driver.setId(id);
        driver.setName("Ralph");
        driver.setAge(33);
        Set<Category> categories = new HashSet<>();
        categories.add(Category.D);
        driver.setCategories(categories);
        driver.setCar(Car.DAF);
        driver.setCarNumber("1234-ab1");
        driver.setFuelRate100();
        return driver;
    }

    public static Trip getTripByRoute(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriver(getDriver(1L));
        trip.setStartPoint("brest");
        trip.setEndPoint("minsk");
        trip.setDistance(350d);
        trip.setStartDate(formatter.parse("2016-07-14"));
        trip.setEndDate(formatter.parse("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static List<Trip> getTripsByRoute() throws ParseException {
        List<Trip> trips = new ArrayList<>();
        trips.add(getTripByRoute(1L));
        trips.add(getTripByRoute(2L));
        return trips;
    }


}
