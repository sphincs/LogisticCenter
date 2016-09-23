package com.sphincs.domain;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;

public class DomainTests {

    Driver driver;

    @Test
    public void DriverFieldsTest() {
        String name = "Vasili Ivanych";
        int age = 48;

        driver = new Driver(name, age);
        Assert.assertEquals(name, driver.getName());
        Assert.assertEquals(age, driver.getAge());
    }

    @Test
    public void DriverEqualsTest() {

        driver = new Driver();

        Driver driver1 = new Driver("Vasili Ivanych", 48);
        Driver driver2 = new Driver("Vasili Ivanych", 48);
        Driver driver3 = new Driver("Vasili Ivanych", 20);

        Assert.assertEquals(driver, driver);
        Assert.assertEquals(driver1, driver2);

        Assert.assertFalse(driver.equals(driver1));
        Assert.assertFalse(driver3.equals(driver2));
        Assert.assertFalse(driver1.equals(driver3));
    }


    private Trip trip;

    @Test
    public void TripFieldsTest() throws ParseException {
        Driver driver = new Driver("Vasili Ivanych", 48);
        String driverName = driver.getName();
        String car = "FORD";
        Double fuelRate100 = 7.0d;
        String startPoint = "Brest";
        String endPoint = "Minsk";
        String distance = "340";
        Date startDate = Date.valueOf("2016-06-27");
        Date endDate = Date.valueOf("2016-06-28");
        Double sumFuel = 23.8;

        trip = new Trip(driverName, car, fuelRate100, startPoint, endPoint, distance, startDate, endDate);
        Assert.assertEquals("Vasili Ivanych", trip.getDriverName());
        Assert.assertEquals(car, trip.getCar());
        Assert.assertEquals(fuelRate100, trip.getFuelRate100());
        Assert.assertEquals(startPoint, trip.getStartPoint());
        Assert.assertEquals(endPoint, trip.getEndPoint());
        Assert.assertEquals(distance, trip.getDistance());
        Assert.assertEquals(startDate, trip.getStartDate());
        Assert.assertEquals(endDate, trip.getEndDate());
        Assert.assertEquals(String.format("%.2f", sumFuel), trip.getSumFuel());
    }

    @Test
    public void TripEqualsTest() throws ParseException {
        trip = new Trip();
        Driver driver = new Driver("Vasili Ivanych", 48);

        Trip trip1 = new Trip(driver.getName(), "FORD", 7.0, "Brest", "Minsk", "340", Date.valueOf("2016-06-27"), Date.valueOf("2016-06-28"));
        Trip trip2 = new Trip(driver.getName(), "FORD", 7.0, "Brest", "Minsk", "340", Date.valueOf("2016-06-27"), Date.valueOf("2016-06-28"));
        Trip trip3 = new Trip(driver.getName(), "FORD", 7.0, "Brest", "Minsk", "340", Date.valueOf("2016-06-28"), Date.valueOf("2016-06-28"));

        Assert.assertEquals(trip, trip);
        Assert.assertEquals(trip1, trip2);

        Assert.assertFalse(trip.equals(trip1));
        Assert.assertFalse(trip3.equals(trip2));
        Assert.assertFalse(trip1.equals(trip3));
    }

}
