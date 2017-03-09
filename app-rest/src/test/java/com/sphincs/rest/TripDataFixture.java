package com.sphincs.rest;

import com.sphincs.domain.Trip;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TripDataFixture {

    public static Trip getNewTrip() throws ParseException {
        Trip trip = new Trip();
        trip.setDriver(DriverDataFixture.getNewDriver());
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Brest");
        trip.setEndPoint("Minsk");
        trip.setDistance("350");
        trip.setStartDate(Date.valueOf("2016-07-14"));
        trip.setEndDate(Date.valueOf("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static Trip getNewTripWithId(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriver(DriverDataFixture.getNewDriver());
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Gomel");
        trip.setEndPoint("Rome");
        trip.setDistance("2530");
        trip.setStartDate(Date.valueOf("2016-07-05"));
        trip.setEndDate(Date.valueOf("2016-07-08"));
        trip.setSumFuel();
        return trip;
    }

    public static Trip getNewTripWithDriverRalph(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriver(DriverDataFixture.getNewDriver());
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Brest");
        trip.setEndPoint("Minsk");
        trip.setDistance("350");
        trip.setStartDate(Date.valueOf("2016-07-14"));
        trip.setEndDate(Date.valueOf("2016-07-15"));
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

    public static Trip getTripByRoute(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriver(DriverDataFixture.getNewDriver());
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Brest");
        trip.setEndPoint("Minsk");
        trip.setDistance("350");
        trip.setStartDate(Date.valueOf("2016-07-14"));
        trip.setEndDate(Date.valueOf("2016-07-15"));
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
