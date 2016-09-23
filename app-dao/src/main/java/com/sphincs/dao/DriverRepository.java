package com.sphincs.dao;

import com.sphincs.domain.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Long> {

    Driver findByName(String name);

}
