package com.sphincs.service;

import com.sphincs.domain.Driver;

import java.util.List;

public interface DriverService {

    void save(Driver driver);

    List<Driver> findAll();

    Driver findOne(Long id);

    long count();

    Driver findByName(String name);

    void delete(Long id);

}
