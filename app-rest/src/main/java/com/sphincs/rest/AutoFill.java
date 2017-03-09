package com.sphincs.rest;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.DriverService;
import com.sphincs.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.text.ParseException;

public class AutoFill {

    private final DriverService driverService;
    private final TripService tripService;
    private static Driver driver1;
    private static Driver driver2;
    private static Driver driver3;
    private static Driver driver4;
    private static Driver driver5;
    private static Driver driver6;
    private static Driver driver7;
    private static Trip trip1;
    private static Trip trip2;
    private static Trip trip3;
    private static Trip trip4;

    @Autowired
    public AutoFill(DriverService driverService, TripService tripService) {
        this.driverService = driverService;
        this.tripService = tripService;
    }

    public void getFillTables() throws ParseException {
        fillDrivers();
        fillTrips();
    }

    private void fillDrivers() {
        driver1 = driverService.save(new Driver("Mike", 35));
        driver2 = driverService.save(new Driver("Bobby", 42));
        driver3 = driverService.save(new Driver("Misha", 36));
        driver4 = driverService.save(new Driver("Boris", 37));
        driver5 = driverService.save(new Driver("Spencer", 50));
        driver6 = driverService.save(new Driver("Rob", 36));
        driver7 = driverService.save(new Driver("Mitchel", 28));
    }

    private void fillTrips() throws ParseException {
        trip1 = tripService.save(new Trip(driver1, "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")));
        trip2 = tripService.save(new Trip(driver2, "FORD", 7.5, "Moscow", "Astana", "2800", Date.valueOf("2016-06-30"), Date.valueOf("2016-07-02")));
        trip3 = tripService.save(new Trip(driver3, "BMW", 7.2, "Gomel", "Rome", "2530", Date.valueOf("2016-07-01"), Date.valueOf("2016-07-03")));
        trip4 = tripService.save(new Trip(driver4, "LADA", 5.5, "Chicago", "Dallas", "3000", Date.valueOf("2016-07-04"), Date.valueOf("2016-07-06")));
    }

}
