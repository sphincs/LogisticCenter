package com.sphincs.service;

import com.google.common.collect.Lists;
import com.sphincs.dao.DriverRepository;
import com.sphincs.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> findAllDrivers() {
        return Lists.newArrayList(driverRepository.findAll());
    }

    @Override
    public Driver findDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> notFoundException("id", id.toString()));
    }

    @Override
    public long count() {
        return driverRepository.count();
    }

    @Override
    public Driver findByName(String name) {
        return driverRepository.findByName(name)
                .orElseThrow(() -> notFoundException("name", name));
    }

    @Override
    public void delete(Long id) {
        driverRepository.delete(id);
    }

    private LogisticException notFoundException(String param, String value) {
        return new LogisticException(String.format("Driver with %s = %s not found.", param, value), HttpStatus.OK);
    }

}
