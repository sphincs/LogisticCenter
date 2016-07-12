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
        Assert.notNull(trip.getDriver(), "Trip's driver should be specified.");
        Assert.notNull(trip.getStartPoint(), "Trip's startPoint should be specified.");
        Assert.notNull(trip.getEndPoint(), "Trip's endPoint should be specified.");
        Assert.notNull(trip.getDistance(), "Trip's distance should be specified.");
        Assert.isTrue(trip.getDistance() > 0, "Trip's distance should be more than 0 km.");
        Assert.notNull(trip.getStartDate(), "Trip's startDate should be specified.");
        Assert.notNull(trip.getEndDate(), "Trip's endDate should be specified.");
        Assert.notNull(trip.getSumFuel(), "Trip's summary fuel rate should be specified.");

        List<Trip> existingTrips = tripDao.getTripsByRoute(trip.getStartPoint(), trip.getEndPoint());
        Trip existingTrip = null;

        for (Trip current : existingTrips) {
            if (current.getDriver().equals(trip.getDriver()) &&
                current.getDistance().equals(trip.getDistance()) &&
                current.getStartDate().equals(trip.getStartDate()) &&
                current.getEndDate().equals(trip.getEndDate()) &&
                current.getSumFuel().equals(trip.getSumFuel())) {
                existingTrip = current;
            }
        }

        if(existingTrip != null) {
            throw new IllegalArgumentException(trip + " is present in DB");
        }

        return tripDao.addTrip(trip);
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
        List<Trip> trips = new ArrayList<>();
        try {
            trips = tripDao.getTripsByDriver(name);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getTripsByDriver({}), Exception:{}", name, e.toString());
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
        Trip modifyTrip = null;
        try {
            modifyTrip = tripDao.getTripById(trip.getId());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("updateTrip({}): Exception:{}", trip, e.toString() );
            return;
        }

        try {
            tripDao.updateTrip(trip);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("updateTrip({}), Exception:{}",trip, e.toString());
        }
    }
}
