package com.sphincs.dao;

import com.sphincs.domain.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TripDaoImpl implements TripDao {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TRIP_ID = "tripid";
    private static final String DRIVER = "tripdriver";
    private static final String START_POINT = "startpoint";
    private static final String END_POINT = "endpoint";
    private static final String DISTANCE = "distance";
    private static final String START_DATE = "startdate";
    private static final String END_DATE = "enddate";
    private static final String SUM_FUEL = "sumfuel";

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${add_trip_path}')).inputStream)}")
    public String addTrip;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_all_trips_path}')).inputStream)}")
    public String getAllTrips;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${remove_trip_path}')).inputStream)}")
    public String removeTrip;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_trip_by_id_path}')).inputStream)}")
    public String getTripById;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_trips_by_driver_path}')).inputStream)}")
    public String getTripsByDriver;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_trips_by_route_path}')).inputStream)}")
    public String getTripsByRoute;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_trips_by_date_path}')).inputStream)}")
    public String getTripsByDate;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_trip_path}')).inputStream)}")
    public String updateTrip;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DriverDao driverDao;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDriverDao(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public Long addTrip(Trip trip) {
        LOGGER.debug("addTrip({}) ", trip);
        Assert.notNull(trip);
        Assert.isNull(trip.getId());
        Assert.notNull(trip.getDriver(), "Trip's driver should be specified.");
        Assert.notNull(trip.getStartPoint(), "Trip's startPoint should be specified.");
        Assert.notNull(trip.getEndPoint(), "Trip's endPoint should be specified.");
        Assert.notNull(trip.getDistance(), "Trip's distance should be specified.");
        Assert.isTrue(trip.getDistance() > 0, "Trip's distance should be more than 0 km.");
        Assert.notNull(trip.getStartDate(), "Trip's startDate should be specified.");
        Assert.notNull(trip.getEndDate(), "Trip's endDate should be specified.");
        Assert.notNull(trip.getSumFuel(), "Trip's summary fuel rate should be specified.");

        Map<String, Object> params = new HashMap<>(8);
        params.put(TRIP_ID, trip.getId());
        params.put(DRIVER, trip.getDriver().getId());
        params.put(START_POINT, trip.getStartPoint());
        params.put(END_POINT, trip.getEndPoint());
        params.put(DISTANCE, trip.getDistance());
        params.put(START_DATE, trip.getStartDate());
        params.put(END_DATE, trip.getEndDate());
        params.put(SUM_FUEL, trip.getSumFuel());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(addTrip, new MapSqlParameterSource(params), keyHolder);
        LOGGER.debug("addDriver: id{} ", keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Trip> getAllTrips() {
        LOGGER.debug("getAllDrivers");
        return jdbcTemplate.query(getAllTrips, new TripMapper());
    }

    @Override
    public void removeTrip(Long id) {
        LOGGER.debug("removeTrip({}) ", id);
        jdbcTemplate.update(removeTrip, id);
    }

    @Override
    public Trip getTripById(Long id) {
        LOGGER.debug("getTripById({}) ", id);
        return jdbcTemplate.queryForObject(getTripById, new TripMapper(), id);
    }

    @Override
    public List<Trip> getTripsByDriver(String name) {
        LOGGER.debug("getTripsByDriver({}) ", name);
        return jdbcTemplate.query(getTripsByDriver, new TripMapper(), driverDao.getDriverByName(name).getId());
    }

    @Override
    public List<Trip> getTripsByRoute(String startPoint, String endPoint) {
        LOGGER.debug("getTripsByRoute({}) ", startPoint, " - ", endPoint);
        return jdbcTemplate.query(getTripsByRoute, new TripMapper(), startPoint, endPoint);
    }

    @Override
    public List<Trip> getTripsByDate(Date startDate, Date endDate) {
        LOGGER.debug("getTripsByDate(from {} to {}) ", formatter.format(startDate), formatter.format(endDate));
        return jdbcTemplate.query(getTripsByDate, new TripMapper(), new Date[]{startDate, endDate});
    }

    @Override
    public void updateTrip(Trip trip) {
        LOGGER.debug("updateTrip({}) ", trip);
        Assert.notNull(trip, "Trip should not be null");
        Assert.notNull(trip.getId(), "Trip's id should not be null");
        Assert.notNull(trip.getDriver(), "Trip's driver should be specified.");
        Assert.notNull(trip.getStartPoint(), "Trip's startPoint should be specified.");
        Assert.notNull(trip.getEndPoint(), "Trip's endPoint should be specified.");
        Assert.notNull(trip.getDistance(), "Trip's distance should be specified.");
        Assert.notNull(trip.getStartDate(), "Trip's startDate should be specified.");
        Assert.notNull(trip.getEndDate(), "Trip's endDate should be specified.");
        Assert.notNull(trip.getSumFuel(), "Trip's summary fuel rate should be specified.");

        Map<String, Object> params = new HashMap<>(8);
        params.put(TRIP_ID, trip.getId());
        params.put(DRIVER, trip.getDriver().getId());
        params.put(START_POINT, trip.getStartPoint());
        params.put(END_POINT, trip.getEndPoint());
        params.put(DISTANCE, trip.getDistance());
        params.put(START_DATE, trip.getStartDate());
        params.put(END_DATE, trip.getEndDate());
        //params.put(START_DATE, formatter.format(trip.getStartDate()));
        //params.put(END_DATE, formatter.format(trip.getEndDate()));
        params.put(SUM_FUEL, trip.getSumFuel());

        namedJdbcTemplate.update(updateTrip, params);
    }

    public class TripMapper implements RowMapper<Trip> {
        @Override
        public Trip mapRow(ResultSet resultSet, int i) throws SQLException {
            Trip trip = new Trip();
            trip.setId(resultSet.getLong(TRIP_ID));
            trip.setDriver(driverDao.getDriverById(resultSet.getLong(DRIVER)));//new DriverDaoImpl().getDriverById(resultSet.getLong(DRIVER)));
            trip.setStartPoint(resultSet.getString(START_POINT));
            trip.setEndPoint(resultSet.getString(END_POINT));
            trip.setDistance(resultSet.getDouble(DISTANCE));
            trip.setStartDate(resultSet.getDate(START_DATE));
            trip.setEndDate(resultSet.getDate(END_DATE));
            trip.setSumFuel();
            return trip;
        }
    }



}
