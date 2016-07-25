package com.sphincs.service;

import com.sphincs.dao.DriverDao;
import com.sphincs.dao.TripDao;
import com.sphincs.domain.Car;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    public static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private DriverDao driverDao;

    @Autowired
    private TripDao tripDao;

    @Override
    public Long addDriver(Driver driver) {
        LOGGER.debug("addDriver({}) ", driver);

        Assert.notNull(driver);
        Assert.isNull(driver.getId());
        Assert.notNull(driver.getName(), "Driver's name should be specified.");
        Assert.notNull(driver.getAge(), "Driver's age should be specified.");
        Assert.isTrue(driver.getAge() > 18, "Driver's age should be more than 18 years old.");
        Assert.notNull(driver.getCategories(), "Driver's categories should be specified.");
        Assert.notNull(driver.getCar(), "Driver's car should be specified.");
        Assert.notNull(driver.getCarNumber(), "Driver's car number should be specified.");
        Assert.isTrue(driver.getFuelRate100() != 0D, "Fuel rate should be more than 0. Incorrect car");

        Driver existignDriver = getDriverByName(driver.getName());

        if (existignDriver != null) {
            throw new IllegalArgumentException(driver + " is present in DB");
        }

        return driverDao.addDriver(driver);
    }

    @Override
    @Transactional
    public List<Driver> getAllDrivers() {
        LOGGER.debug("getAllDrivers({})");
        return driverDao.getAllDrivers();
    }

    @Override
    @Transactional
    public void removeDriver(Long id) {
        LOGGER.debug("removeDriver({}) ", id);
        List<Trip> trips;
        try {
            trips = tripDao.getTripsByDriver(driverDao.getDriverById(id).getName());
            for (Trip currentTrip : trips) {
                tripDao.removeTrip(currentTrip.getId());
            }
            driverDao.removeDriver(id);
        } catch (IncorrectResultSizeDataAccessException e) {
            driverDao.removeDriver(id);
        }
    }

    @Override
    @Transactional
    public Driver getDriverById(Long id) {
        LOGGER.debug("getDriverById({}) ", id);
        Driver driver = null;
        try {
            driver = driverDao.getDriverById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getDriverById({}), Exception:{}", id, e.toString());
        }
        return driver;
    }

    @Override
    @Transactional
    public Driver getDriverByName(String name) {
        LOGGER.debug("getDriverByName({}) ", name);
        Driver driver = null;
        try {
            driver = driverDao.getDriverByName(name);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getDriverByName({}), Exception:{}", name, e.toString());
        }
        return driver;
    }

    @Override
    @Transactional
    public Driver getDriverByCarNumber(String carNumber) {
        LOGGER.debug("getDriverByNameCarNumber({}) ", carNumber);
        Driver driver = null;
        try {
            driver = driverDao.getDriverByCarNumber(carNumber);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getDriverByNameCarNumber({}), Exception:{}", carNumber, e.toString());
        }
        return driver;
    }

    @Override
    @Transactional
    public List<Driver> getDriversByCar(Car car) {
        LOGGER.debug("getDriversByCar({}) ", car);
        List<Driver> drivers = new ArrayList<>();
        try {
            drivers = driverDao.getDriversByCar(car);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getDriversByCar({}), Exception:{}", car, e.toString());
        }
        return drivers;
    }

    @Override
    @Transactional
    public void updateDriver(Driver driver) {
        LOGGER.debug("updateDriver({}) ", driver);
        Assert.notNull(driver);
        Driver modifyDriver = null;
        try {
            modifyDriver = driverDao.getDriverById(driver.getId());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("updateDriver({}): Exception:{}", driver, e.toString());
            return;
        }

        try {
            driverDao.updateDriver(driver);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("updateDriver({}), Exception:{}", driver, e.toString());
        }
    }
}
