package com.sphincs.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripTest {

    private Trip trip;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void TripFieldsTest() throws ParseException {
        Long id = 4L;
        Driver driver = new Driver(1L, "Vasili Ivanych", 48);
        String driverName = driver.getName();
        String car = "FORD";
        Double fuelRate100 = 7.0d;
        String startPoint = "Brest";
        String endPoint = "Minsk";
        String distance = "340";
        Date startDate = format.parse("2016-06-27");
        Date endDate = format.parse("2016-06-28");
        Double sumFuel = 23.8;

        trip = new Trip(id, driverName, car, fuelRate100, startPoint, endPoint, distance, startDate, endDate);
        Assert.assertEquals(id, trip.getId());
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
        Driver driver = new Driver(1L, "Vasili Ivanych", 48);

        Trip trip1 = new Trip(1L, driver.getName(), "FORD", 7.0, "Brest", "Minsk", "340", format.parse("2016-06-27"), format.parse("2016-06-28"));
        Trip trip2 = new Trip(1L, driver.getName(), "FORD", 7.0, "Brest", "Minsk", "340", format.parse("2016-06-27"), format.parse("2016-06-28"));
        Trip trip3 = new Trip(1L, driver.getName(), "FORD", 7.0, "Brest", "Minsk", "340", format.parse("2016-06-28"), format.parse("2016-06-28"));

        Assert.assertEquals(trip, trip);
        Assert.assertEquals(trip1, trip2);

        Assert.assertFalse(trip.equals(trip1));
        Assert.assertFalse(trip3.equals(trip2));
        Assert.assertFalse(trip1.equals(trip3));
    }

}
