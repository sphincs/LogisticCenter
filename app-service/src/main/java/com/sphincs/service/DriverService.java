package com.sphincs.service;

import com.sphincs.domain.Driver;

import java.util.List;

public interface DriverService {

    Driver save(Driver driver);

    List<Driver> findAll();

    Driver findById(Long id);

    long count();

    Driver findByName(String name);

    void delete(Long id);

}
