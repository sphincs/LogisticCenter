package com.sphincs.service;

import com.sphincs.domain.Trip;
import java.util.Date;
import java.util.List;

public interface TripService {

    Long addTrip(Trip trip);
    List<Trip> getAllTrips();
    void removeTrip(Long id);
    Trip getTripById(Long id);
    List<Trip> getTripsByDriver(String name);
    List<Trip> getTripsByRoute(String startPoint, String endPoint);
    List<Trip> getTripsByDate(Date startDate, Date endDate);
    void updateTrip(Trip trip);

}
