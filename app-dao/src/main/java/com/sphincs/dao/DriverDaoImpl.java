package com.sphincs.dao;

import com.sphincs.domain.Car;
import com.sphincs.domain.Category;
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
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//@Repository
public class DriverDaoImpl implements DriverDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DRIVER_ID = "driverid";
    private static final String NAME = "drivername";
    private static final String AGE = "age";
    private static final String CATEGORY = "category";
    private static final String CAR = "car";
    private static final String CAR_NUMBER = "carnumber";
    private static final String FUEL_RATE = "fuelrate";


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

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_drivers_by_car_path}')).inputStream)}")
    public String getDriversByCar;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_driver_path}')).inputStream)}")
    public String updateDriver;


        @Autowired
        private DataSource dataSource;

        public void setDataSource(DriverManagerDataSource dataSource) {
            this.dataSource = dataSource;
        }

        private JdbcTemplate jdbcTemplate;
        private NamedParameterJdbcTemplate namedJdbcTemplate;

        @PostConstruct
        public void init () {
            jdbcTemplate = new JdbcTemplate(dataSource);
            namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        }


    @Override
    public Long addDriver(Driver driver) {
        LOGGER.debug("addDriver({}) ", driver);
        Assert.notNull(driver);
        Assert.isNull(driver.getId());
        Assert.notNull(driver.getName(), "Driver's name should be specified.");
        Assert.notNull(driver.getAge(), "Driver's age should be specified.");
        Assert.isTrue(driver.getAge() > 18, "Driver's age should be more than 18 years old.");
        Assert.notNull(driver.getCategories(), "Driver's categories should be specified.");
        Assert.notNull(driver.getCar(), "Driver's car should be specified.");
        Assert.notNull(driver.getCarNumber(), "Driver's car number should be specified.");
        Assert.isTrue(driver.getFuelRate100() != 0D, "Fuel rate should be more than 0. Incorrect car");

        Map<String, Object> params = new HashMap<>(6);
        params.put(NAME, driver.getName());
        params.put(AGE, driver.getAge());
        String categories = "";
        for (Category current : driver.getCategories()) {
            categories += current.ordinal() + " ";
        }
        params.put(CATEGORY, categories.trim());
        params.put(CAR, driver.getCar().ordinal());
        params.put(CAR_NUMBER, driver.getCarNumber());
        params.put(FUEL_RATE, driver.getFuelRate100());

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
    public List<Driver> getDriversByCar(Car car) {
        LOGGER.debug("getDriversByCar({}) ", car);
        Integer carIndex = car.ordinal();
        return jdbcTemplate.query(getDriversByCar, new DriverMapper(), carIndex);
    }

    @Override
    public void updateDriver(Driver driver) {
        LOGGER.debug("addDriver({}) ", driver);
        Assert.notNull(driver, "Driver should not be null");
        Assert.notNull(driver.getId(),"Driver's id should be specified.");
        Assert.notNull(driver.getName(), "Driver's name should be specified.");
        Assert.notNull(driver.getAge(), "Driver's age should be specified.");
        Assert.notNull(driver.getCategories(), "Driver's categories should be specified.");
        Assert.notNull(driver.getCar(), "Driver's car should be specified.");
        Assert.notNull(driver.getCarNumber(), "Driver's car number should be specified.");

        Map<String, Object> params = new HashMap<>(7);
        params.put(DRIVER_ID, driver.getId());
        params.put(NAME, driver.getName());
        params.put(AGE, driver.getAge());
        String categories = "";
        for (Category current : driver.getCategories()) {
            categories += current.ordinal() + " ";
        }
        params.put(CATEGORY, categories.trim());
        params.put(CAR, driver.getCar().ordinal());
        params.put(CAR_NUMBER, driver.getCarNumber());
        params.put(FUEL_RATE, driver.getFuelRate100());

        namedJdbcTemplate.update(updateDriver, params);
    }

    public class DriverMapper implements RowMapper<Driver> {
        @Override
        public Driver mapRow(ResultSet resultSet, int i) throws SQLException {
            Driver driver = new Driver();
            driver.setId(resultSet.getLong(DRIVER_ID));
            driver.setName(resultSet.getString(NAME));
            driver.setAge(resultSet.getInt(AGE));
            String[] categoryArray = resultSet.getString(CATEGORY).split(" ");
            Set<Category> categorySet = new HashSet<>();
            for (int j = 0; j < categoryArray.length; j++) {
                categorySet.add(Category.getByIndex(Integer.parseInt(categoryArray[j])));
            }
            driver.setCategories(categorySet);
            driver.setCar(Car.getByIndex(resultSet.getInt(CAR)));
            driver.setCarNumber(resultSet.getString(CAR_NUMBER));
            driver.setFuelRate100();
            return driver;
        }
    }


}
