package com.sphincs.service;

import com.sphincs.domain.Driver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DriverService {

    Driver save(Driver driver);

    List<Driver> findAllDrivers();

    Driver findDriverById(Long id);

    long count();

    Driver findByName(String name);

    void delete(Long id);

}
