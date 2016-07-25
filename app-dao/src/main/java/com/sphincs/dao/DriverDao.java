package com.sphincs.dao;

import com.sphincs.domain.Car;
import com.sphincs.domain.Driver;

import java.util.List;

public interface DriverDao {

    Long addDriver(Driver driver);
    List<Driver> getAllDrivers();
    void removeDriver(Long id);
    Driver getDriverById(Long id);
    Driver getDriverByName(String name);
    List<Driver> getDriversByCar(Car car);
    void updateDriver(Driver driver);
    Driver getDriverByCarNumber(String carNumber);

}
