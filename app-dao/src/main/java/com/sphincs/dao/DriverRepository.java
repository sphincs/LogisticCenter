package com.sphincs.dao;

import com.sphincs.domain.Driver;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Long> {

    Optional<Driver> findByName(String name);

    Optional<Driver> findById(Long id);

}
