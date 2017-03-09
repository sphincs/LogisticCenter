package com.sphincs.dao;

import com.sphincs.domain.Driver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface DriverRepository extends CrudRepository<Driver, Long> {

    Optional<Driver> findByName(String name);

    Optional<Driver> findById(Long id);

}
