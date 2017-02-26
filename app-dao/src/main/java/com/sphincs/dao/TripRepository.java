package com.sphincs.dao;

import com.sphincs.domain.Trip;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends CrudRepository<Trip, Long> {

    Optional<Trip> findById(Long id);

    List<Trip> findByDriverName(String driverName);

    List<Trip> findByCar(String car);

    List<Trip> findByStartPointAndEndPoint(String startPoint, String endPoint);

    List<Trip> findByStartDateBetweenAndEndDateBetween(Date startDate1, Date endDate1,
                                                       Date startDate2, Date endDate2);

}
