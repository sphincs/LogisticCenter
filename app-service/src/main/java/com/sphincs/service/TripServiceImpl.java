package com.sphincs.service;

import com.google.common.collect.Lists;
import com.sphincs.dao.TripRepository;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TripServiceImpl implements TripService {

    private final DriverService driverService;
    private final TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(DriverService driverService, TripRepository tripRepository) {
        this.driverService = driverService;
        this.tripRepository = tripRepository;
    }

    @Override
    public Trip save(Trip trip) {
        try {
            Driver driver = driverService.findDriverById(trip.getDriverId());
            if (driver == null) {
                throw new LogisticException("This driver not exist. ", HttpStatus.BAD_REQUEST);
            }
            if (trip.getStartDate().after(trip.getEndDate()))
                throw new LogisticException("Check the dates, please. ", HttpStatus.BAD_REQUEST);
            if (isDriverFree(driver, trip)) {
                trip.setSumFuel();
                Trip oldTrip = new Trip();
                for (Trip current : driver.getTrips()) {
                    if (current.getId() == trip.getId()) {
                        oldTrip = current;
                    }
                }
                driver.removeTrip(oldTrip);
                driver.addTrip(trip);
                trip.setDriver(driver);
                return tripRepository.save(trip);
            } else throw new LogisticException("This driver not available in this dates. ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new LogisticException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
     public List<Trip> findAllTrips() {
        return Lists.newArrayList(tripRepository.findAll());
    }

    @Override
    public Trip findTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> notFoundException("id", id.toString()));
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
    public List<Trip> findByDriver(Driver driver) {
        List<Trip> trips = Lists.newArrayList(tripRepository.findByDriver(driver));
        return trips;
    }

    @Override
    public List<Trip> findByCar(String car) {
        List<Trip> trips = Lists.newArrayList(tripRepository.findByCar(car));
        if (trips.isEmpty()) {
            throw new LogisticException(String.format("Trips with car = %s not found.", car), HttpStatus.OK);
        }
        return trips;
    }

    @Override
    public List<Trip> findByStartPointAndEndPoint(String startPoint, String endPoint) {
        List<Trip> trips = Lists.newArrayList(tripRepository.findByStartPointAndEndPoint(startPoint, endPoint));
        if (trips.isEmpty()) {
            throw new LogisticException(
                    String.format("Trips with startPoint = %s and endPoint = %s not found.", startPoint, endPoint),
                    HttpStatus.OK);
        }
        return trips;
    }

    @Override
    public List<Trip> findByStartDateAndEndDate(Date startDate, Date endDate) {
        List<Trip> trips = Lists.newArrayList(tripRepository.findByStartDateBetweenAndEndDateBetween(
                startDate, endDate,
                startDate, endDate));
        if (trips.isEmpty()) {
            throw new LogisticException(
                    String.format("Trips with startDate = %s and endDate = %s not found.",
                            startDate.toString(), endDate.toString()),
                    HttpStatus.OK);
        }
        return trips;
    }

    private boolean isDriverFree(Driver driver, Trip trip) {
        Date start = trip.getStartDate();
        Date end = trip.getEndDate();
        List<Trip> trips = findByDriver(driver);
        if (trips.isEmpty()) return true;
        for (Trip current : trips) {
            if ((current.getId() != trip.getId())
                    && ( (start.before(current.getStartDate()) && end.after(current.getStartDate()))
                        || (start.after(current.getStartDate()) && start.before(current.getEndDate())) )
                    ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<String, Object> countFuelByDriver(String name) {
        List<Trip> trips = findByDriver(driverService.findByName(name));
        Map<String, Object> result = new HashMap<>();
        Double sum = 0d;
        for (Trip current : trips) {
            sum += Double.parseDouble(current.getSumFuel().replace(',', '.'));
        }
        result.put("driver", name);
        result.put("sum", String.format("%.2f", sum));
        result.put("trips", trips);
        return result;
    }

    @Override
    public Map<String, Object> countFuelByDate(String startDate, String endDate) {
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);
        List<Trip> trips = findByStartDateAndEndDate(start, end);
        Double sum = 0d;
        if (!trips.isEmpty()) {
            for (Trip current : trips) {
                sum += Double.parseDouble(current.getSumFuel().replace(',', '.'));
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("sum", String.format("%.2f", sum));
        result.put("trips", trips);
        return result;
    }

    private LogisticException notFoundException(String param, String value) {
        return new LogisticException(String.format("Trip with %s = %s not found.", param, value), HttpStatus.OK);
    }

}
