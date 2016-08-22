package com.sphincs.rest;

import com.sphincs.domain.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TripDataFixture {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Trip getNewTrip() throws ParseException {
        Trip trip = new Trip();
        trip.setDriverName("Mike");
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Brest");
        trip.setEndPoint("Minsk");
        trip.setDistance("350");
        trip.setStartDate(formatter.parse("2016-07-14"));
        trip.setEndDate(formatter.parse("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static Trip getNewTripWithId(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriverName("Mike");
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Gomel");
        trip.setEndPoint("Rome");
        trip.setDistance("2530");
        trip.setStartDate(formatter.parse("2016-07-05"));
        trip.setEndDate(formatter.parse("2016-07-08"));
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
        trip.setDriverName("Ralph");
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Brest");
        trip.setEndPoint("Minsk");
        trip.setDistance("350");
        trip.setStartDate(formatter.parse("2016-07-14"));
        trip.setEndDate(formatter.parse("2016-07-15"));
        trip.setSumFuel();
        return trip;
    }

    public static Trip getBadNewTrip() throws ParseException {
        Trip trip = new Trip();
        trip.setDriverName("Mike");
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint(null);
        trip.setEndPoint("Minsk");
        trip.setDistance("350");
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

    public static Trip getTripByRoute(Long id) throws ParseException {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDriverName("Mike");
        trip.setCar("VOLVO");
        trip.setFuelRate100(15.0d);
        trip.setStartPoint("Brest");
        trip.setEndPoint("Minsk");
        trip.setDistance("350");
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

    public static List<Trip> getTripsByCar() throws ParseException {
        List<Trip> trips = new ArrayList<>();
        trips.add(getNewTripWithId(1L));
        trips.add(getNewTripWithId(2L));
        return trips;
    }

}
