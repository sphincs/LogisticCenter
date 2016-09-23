package com.sphincs.service;

import com.sphincs.domain.Trip;

import java.sql.Date;
import java.util.List;

public interface TripService {

    void save(Trip trip);

    List<Trip> findAll();

    Trip findById(Long id);

    long count();

    void delete(Long id);

    List<Trip> findByDriverName(String driverName);

    List<Trip> findByCar(String car);

    List<Trip> findByStartPointAndEndPoint(String startPoint, String endPoint);

    List<Trip> findByStartDateAndEndDate(Date startDate, Date endDate);

}
