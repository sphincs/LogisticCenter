package com.sphincs.domain;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TestTrip extends Assert {

    private Trip trip;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Test
    public void TestTripFields() throws ParseException {
        Long id = 4L;
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        Driver driver = new Driver(1L, "Vasili Ivanych", 48, categories, Car.FORD, "0013ih1");
        String startPoint = "Brest";
        String endPoint = "Minsk";
        Integer distance = 340;
        Date startDate = format.parse("27.06.2016 12:43:15");
        Date endDate = format.parse("28.06.2016 12:43:15");
        Double sumFuel = 21.08;

        trip = new Trip(id, driver, startPoint, endPoint, distance, startDate, endDate);
        assertEquals(id, trip.getId());
        assertEquals(driver, trip.getDriver());
        assertEquals(startPoint, trip.getStartPoint());
        assertEquals(endPoint, trip.getEndPoint());
        assertEquals(distance, trip.getDistance());
        assertEquals(startDate, trip.getStartDate());
        assertEquals(endDate, trip.getEndDate());
        assertEquals(sumFuel, trip.getSumFuel());

    }

    @Test
    public void TestTripEquals() throws ParseException {

        trip = new Trip();
        Set<Category> categories = new HashSet<>();
        categories.add(Category.B);
        Driver driver = new Driver(1L, "Vasili Ivanych", 48, categories, Car.FORD, "0013ih1");

        Trip trip1 = new Trip(1L, driver, "Brest", "Minsk", 340, format.parse("27.06.2016 12:43:15"), format.parse("28.06.2016 12:43:15"));
        Trip trip2 = new Trip(1L, driver, "Brest", "Minsk", 340, format.parse("27.06.2016 12:43:15"), format.parse("28.06.2016 12:43:15"));
        Trip trip3 = new Trip(1L, driver, "Brest", "Minsk", 340, format.parse("27.06.2016 12:43:20"), format.parse("28.06.2016 12:43:15"));

        assertEquals(trip, trip);
        assertEquals(trip1, trip2);

        assertFalse(trip.equals(trip1));
        assertFalse(trip3.equals(trip2));
        assertFalse(trip1.equals(trip3));
    }

}
