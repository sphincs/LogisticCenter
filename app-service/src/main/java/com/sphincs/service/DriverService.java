package com.sphincs.service;

import com.sphincs.domain.Car;
import com.sphincs.domain.Driver;

import java.util.List;

public interface DriverService {

    Long addDriver(Driver driver);
    List<Driver> getAllDrivers();
    void removeDriver(Long id);
    Driver getDriverById(Long id);
    Driver getDriverByName(String name);
    List<Driver> getDriversByCar(Car car);
    void updateDriver(Driver driver);

}
