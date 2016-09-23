package com.sphincs.service;

import com.google.common.collect.Lists;
import com.sphincs.dao.TripRepository;
import com.sphincs.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public void save(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public List<Trip> findAll() {
        return Lists.newArrayList(tripRepository.findAll());
    }

    @Override
    public Trip findById(Long id) {
        return tripRepository.findOne(id);
    }

    @Override
    public long count() {
        return tripRepository.count();
    }

    @Override
    public void delete(Long id) {
        tripRepository.delete(id);
    }

    @Override
    public List<Trip> findByDriverName(String driverName) {
        return Lists.newArrayList(tripRepository.findByDriverName(driverName));
    }

    @Override
    public List<Trip> findByCar(String car) {
        return Lists.newArrayList(tripRepository.findByCar(car));
    }

    @Override
    public List<Trip> findByStartPointAndEndPoint(String startPoint, String endPoint) {
        return Lists.newArrayList(tripRepository.findByStartPointAndEndPoint(startPoint, endPoint));
    }

    @Override
    public List<Trip> findByStartDateAndEndDate(Date startDate, Date endDate) {
        return Lists.newArrayList(tripRepository.findByStartDateBetweenAndEndDateBetween(   startDate, endDate,
                                                                                            startDate, endDate));
    }

}
