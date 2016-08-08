package com.sphincs.dao;

import com.sphincs.domain.Trip;

import java.util.Date;
import java.util.List;

public interface TripDao {

    Long addTrip(Trip trip);

    List<Trip> getAllTrips();

    void removeTrip(Long id);

    Trip getTripById(Long id);

    List<Trip> getTripsByDriver(String name);

    List<Trip> getTripsByCar(String car);

    List<Trip> getTripsByRoute(String startPoint, String endPoint);

    List<Trip> getTripsByDate(Date startDate, Date endDate);

    void updateTrip(Trip trip);

}
