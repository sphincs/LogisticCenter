package com.sphincs.service;

import com.google.common.collect.Lists;
import com.sphincs.dao.DriverRepository;
import com.sphincs.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;


    @Override
    public void save(Driver driver) {
        driverRepository.save(driver);
    }

    @Override
    public List<Driver> findAll() {
        return Lists.newArrayList(driverRepository.findAll());
    }

    @Override
    public Driver findOne(Long id) {
        return driverRepository.findOne(id);
    }

    @Override
    public long count() {
        return driverRepository.count();
    }

    @Override
    public Driver findByName(String name) {
        return driverRepository.findByName(name);
    }

    @Override
    public void delete(Long id) {
        driverRepository.delete(id);
    }

}
