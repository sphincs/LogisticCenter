package com.sphincs.service;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Transactional
public interface TripService {

    Trip save(Trip trip);

    List<Trip> findAllTrips();

    Trip findTripById(Long id);

    long count();

    void delete(Long id);

    List<Trip> findByDriver(Driver driver);

    List<Trip> findByCar(String car);

    List<Trip> findByStartPointAndEndPoint(String startPoint, String endPoint);

    List<Trip> findByStartDateAndEndDate(Date startDate, Date endDate);

    Map<String, Object> countFuelByDriver(String name);

    Map<String, Object> countFuelByDate(String startDate, String endDate);
}
