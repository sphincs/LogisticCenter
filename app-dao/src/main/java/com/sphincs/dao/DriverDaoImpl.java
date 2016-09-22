package com.sphincs.dao;

import com.sphincs.domain.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DriverDaoImpl implements DriverDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DRIVER_ID = "driverid";
    private static final String NAME = "drivername";
    private static final String AGE = "age";


    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${add_driver_path}')).inputStream)}")
    public String addDriver;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_all_drivers_path}')).inputStream)}")
    public String getAllDrivers;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${remove_driver_path}')).inputStream)}")
    public String removeDriver;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_driver_by_id_path}')).inputStream)}")
    public String getDriverById;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_driver_by_name_path}')).inputStream)}")
    public String getDriverByName;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_driver_path}')).inputStream)}")
    public String updateDriver;


    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addDriver(Driver driver) {
        LOGGER.debug("addDriver({}) ", driver);
        Assert.notNull(driver);
        Assert.isNull(driver.getId());
        Assert.notNull(driver.getName(), "Driver's name should be specified.");
        Assert.isTrue(!driver.getName().isEmpty(), "Driver's name should be specified.");
        Assert.notNull(driver.getAge(), "Driver's age should be specified.");
        Assert.isTrue(driver.getAge() >= 18, "Driver's age should be no less than 18 years old.");

        Map<String, Object> params = new HashMap<>();
        params.put(NAME, driver.getName());
        params.put(AGE, driver.getAge());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(addDriver, new MapSqlParameterSource(params), keyHolder);
        LOGGER.debug("addDriver: id{} ", keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Driver> getAllDrivers() {
        LOGGER.debug("getAllDrivers");
        return jdbcTemplate.query(getAllDrivers, new DriverMapper());
    }

    @Override
    public void removeDriver(Long id) {
        LOGGER.debug("removeDriver({}) ", id);
        jdbcTemplate.update(removeDriver, id);

    }

    @Override
    public Driver getDriverById(Long id) {
        LOGGER.debug("getDriverById({}) ", id);
        return jdbcTemplate.queryForObject(getDriverById, new DriverMapper(), id);
    }

    @Override
    public Driver getDriverByName(String name) {
        LOGGER.debug("getDriverByName({}) ", name);
        return jdbcTemplate.queryForObject(getDriverByName, new DriverMapper(), name);
    }

    @Override
    public void updateDriver(Driver driver) {
        LOGGER.debug("addDriver({}) ", driver);
        Assert.notNull(driver, "Driver should not be null");
        Assert.notNull(driver.getId(), "Driver's id should be specified.");
        Assert.notNull(driver.getName(), "Driver's name should be specified.");
        Assert.isTrue(!driver.getName().equals(""), "Driver's name should be specified.");
        Assert.notNull(driver.getAge(), "Driver's age should be specified.");
        Assert.isTrue(driver.getAge() >= 18, "Driver's age should be no less than 18 years old.");

        Map<String, Object> params = new HashMap<>(7);
        params.put(DRIVER_ID, driver.getId());
        params.put(NAME, driver.getName());
        params.put(AGE, driver.getAge());

        namedJdbcTemplate.update(updateDriver, params);
    }

    public class DriverMapper implements RowMapper<Driver> {
        @Override
        public Driver mapRow(ResultSet resultSet, int i) throws SQLException {
            Driver driver = new Driver();
            driver.setId(resultSet.getLong(DRIVER_ID));
            driver.setName(resultSet.getString(NAME));
            driver.setAge(resultSet.getInt(AGE));
            return driver;
        }
    }

}
