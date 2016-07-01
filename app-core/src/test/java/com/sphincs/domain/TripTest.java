package com.sphincs.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TripTest {

    private Trip trip;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Test
    public void TripFieldsTest() throws ParseException {
        Long id = 4L;
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        Driver driver = new Driver(1L, "Vasili Ivanych", 48, categories, Car.FORD, "0013-ih1");
        String startPoint = "Brest";
        String endPoint = "Minsk";
        Double distance = 340d;
        Date startDate = format.parse("27.06.2016 12:43:15");
        Date endDate = format.parse("28.06.2016 12:43:15");
        Double sumFuel = 21.08;

        trip = new Trip(id, driver, startPoint, endPoint, distance, startDate, endDate);
        Assert.assertEquals(id, trip.getId());
        Assert.assertEquals(driver, trip.getDriver());
        Assert.assertEquals(startPoint, trip.getStartPoint());
        Assert.assertEquals(endPoint, trip.getEndPoint());
        Assert.assertEquals(distance, trip.getDistance());
        Assert.assertEquals(startDate, trip.getStartDate());
        Assert.assertEquals(endDate, trip.getEndDate());
        Assert.assertEquals(sumFuel, trip.getSumFuel());
    }

    @Test
    public void TripEqualsTest() throws ParseException {
        trip = new Trip();
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        Driver driver = new Driver(1L, "Vasili Ivanych", 48, categories, Car.FORD, "0013-ih1");

        Trip trip1 = new Trip(1L, driver, "Brest", "Minsk", 340d, format.parse("27.06.2016 12:43:15"), format.parse("28.06.2016 12:43:15"));
        Trip trip2 = new Trip(1L, driver, "Brest", "Minsk", 340d, format.parse("27.06.2016 12:43:15"), format.parse("28.06.2016 12:43:15"));
        Trip trip3 = new Trip(1L, driver, "Brest", "Minsk", 340d, format.parse("27.06.2016 12:43:20"), format.parse("28.06.2016 12:43:15"));

        Assert.assertEquals(trip, trip);
        Assert.assertEquals(trip1, trip2);

        Assert.assertFalse(trip.equals(trip1));
        Assert.assertFalse(trip3.equals(trip2));
        Assert.assertFalse(trip1.equals(trip3));
    }

}
