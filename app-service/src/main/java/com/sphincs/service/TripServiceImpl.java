package com.sphincs.service;

import com.sphincs.dao.TripDao;
import com.sphincs.domain.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    public static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TripDao tripDao;

    @Override
    public Long addTrip(Trip trip) {
        LOGGER.debug("addTrip({}) ", trip);
        Assert.notNull(trip);
        Assert.isNull(trip.getId());
        Assert.notNull(trip.getDriverName(), "Trip's driver should be specified.");
        Assert.isTrue(!trip.getDriverName().isEmpty(), "Trip's driver should be specified.");
        Assert.notNull(trip.getCar(), "Driver's car should be specified.");
        Assert.isTrue(!trip.getCar().isEmpty(), "Driver's car should be specified.");
        Assert.notNull(trip.getFuelRate100(), "Fuel rate should be specified.");
        Assert.isTrue(trip.getFuelRate100() > 0D, "Fuel rate should be more than 0. Incorrect car");
        Assert.notNull(trip.getStartPoint(), "Trip's startPoint should be specified.");
        Assert.isTrue(!trip.getStartPoint().isEmpty(), "Trip's startPoint should be specified.");
        Assert.notNull(trip.getEndPoint(), "Trip's endPoint should be specified.");
        Assert.isTrue(!trip.getEndPoint().isEmpty(), "Trip's endPoint should be specified.");
        Assert.notNull(trip.getDistance(), "Trip's distance should be specified.");
        Assert.isTrue(!trip.getDistance().isEmpty(), "Trip's distance should be specified.");
        Assert.isTrue(Double.parseDouble(trip.getDistance()) > 0, "Trip's distance should be more than 0 km.");
        Assert.notNull(trip.getStartDate(), "Trip's startDate should be specified.");
        Assert.isTrue(!trip.getStartDate().toString().isEmpty(), "Trip's startDate should be specified.");
        Assert.notNull(trip.getEndDate(), "Trip's endDate should be specified.");
        Assert.isTrue(!trip.getEndDate().toString().isEmpty(), "Trip's endDate should be specified.");

        if (isTripExist(trip)) {
            throw new IllegalArgumentException(trip + " is present in DB");
        }

        return tripDao.addTrip(trip);
    }

    private boolean isTripExist(Trip trip) {
        List<Trip> existingTrips = tripDao.getTripsByRoute(trip.getStartPoint(), trip.getEndPoint());

        for (Trip current : existingTrips) {
            if (current.getDriverName().equals(trip.getDriverName()) &&
                    current.getCar().equals(trip.getCar()) &&
                    current.getFuelRate100().equals(trip.getFuelRate100()) &&
                    current.getDistance().equals(trip.getDistance()) &&
                    current.getStartDate().equals(trip.getStartDate()) &&
                    current.getEndDate().equals(trip.getEndDate()) &&
                    current.getSumFuel().equals(trip.getSumFuel())) {
                return true;
            }
        }

        return false;
    }

    @Override
    @Transactional
    public List<Trip> getAllTrips() {
        LOGGER.debug("getAllTrips{})");
        return tripDao.getAllTrips();
    }

    @Override
    @Transactional
    public void removeTrip(Long id) {
        LOGGER.debug("removeTrip({}) ", id);
        tripDao.removeTrip(id);
    }

    @Override
    @Transactional
    public Trip getTripById(Long id) {
        LOGGER.debug("getTripById({}) ", id);
        Trip trip = null;
        try {
            trip = tripDao.getTripById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getTripById({}), Exception:{}", id, e.toString());
        }
        return trip;
    }

    @Override
    @Transactional
    public List<Trip> getTripsByDriver(String name) {
        LOGGER.debug("getTripsByDriver({}) ", name);
        List<Trip> trips = null;
        try {
            trips = tripDao.getTripsByDriver(name);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getTripsByDriver({}), Exception:{}", name, e.toString());
        }
        return trips;
    }

    @Override
    @Transactional
    public List<Trip> getTripsByCar(String car) {
        LOGGER.debug("getTripsByCar({}) ", car);
        List<Trip> trips = new ArrayList<>();
        try {
            trips = tripDao.getTripsByCar(car);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getTripsByCar({}), Exception:{}", car, e.toString());
        }
        return trips;
    }

    @Override
    @Transactional
    public List<Trip> getTripsByRoute(String startPoint, String endPoint) {
        LOGGER.debug("getTripsByRoute({} - {}) ", startPoint, endPoint);
        List<Trip> trips = new ArrayList<>();
        try {
            trips = tripDao.getTripsByRoute(startPoint, endPoint);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getTripsByRoute({} - {}), Exception:{}", startPoint, endPoint, e.toString());
        }
        return trips;
    }

    @Override
    @Transactional
    public List<Trip> getTripsByDate(Date startDate, Date endDate) {
        LOGGER.debug("getTripsByDate({} - {}) ", startDate, endDate);
        List<Trip> trips = new ArrayList<>();
        try {
            trips = tripDao.getTripsByDate(startDate, endDate);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getTripsByDate({} - {}), Exception:{}", startDate, endDate, e.toString());
        }
        return trips;
    }

    @Override
    @Transactional
    public void updateTrip(Trip trip) {
        LOGGER.debug("updateTrip({}) ", trip);
        Assert.notNull(trip);
        try {
            tripDao.getTripById(trip.getId());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("updateTrip({}): Exception:{}", trip, e.toString());
            return;
        }

        try {
            tripDao.updateTrip(trip);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("updateTrip({}), Exception:{}", trip, e.toString());
        }
    }

}
