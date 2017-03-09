package com.sphincs.service;

import com.google.common.collect.Lists;
import com.sphincs.dao.TripRepository;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TripServiceMockTests {

    @Mock
    protected TripRepository tripRepository;
    @Mock
    protected DriverService driverService;
    @Autowired
    protected TripService tripService;

    @Before
    public void setUp() {
        tripService = new TripServiceImpl(driverService, tripRepository);
    }

    @Test
    public void addTripTest() {
        Trip trip = new Trip(new Driver("Mike", 40),"VOLVO",15.0d,"Brest","Minsk","350",
                Date.valueOf("2016-07-14"),Date.valueOf("2016-07-15"));
        List<Trip> list = Lists.newArrayList(trip);
        given(tripRepository.save(trip)).willReturn(trip);
        given(tripRepository.findByDriver(any(Driver.class))).willReturn(list);
        given(driverService.findDriverById(anyLong())).willReturn(new Driver("Mike", 40));
        tripService.save(trip);
        verify(tripRepository, times(1)).save(trip);
        verify(driverService, times(1)).findDriverById(anyLong());
    }

    @Test
    public void findAllTripsTest() {
        given(tripRepository.findAll()).willReturn(new ArrayList<Trip>());
        tripService.findAllTrips();
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    public void findTripByIdTest() {
        given(tripRepository.findById(1L)).willReturn(Optional.of(new Trip()));
        tripService.findTripById(1L);
        verify(tripRepository, times(1)).findById(1L);
    }

    @Test
    public void countTripTest() {
        given(tripRepository.count()).willReturn(1L);
        tripService.count();
        verify(tripRepository, times(1)).count();
    }

    @Test
    public void deleteTripTest() {
        tripService.delete(1L);
        verify(tripRepository, times(1)).delete(1L);
    }

    @Test
    public void findTripsByDriverTest() {
        List<Trip> list = new ArrayList<>();
        list.add(new Trip());
        given(tripRepository.findByDriver(any(Driver.class))).willReturn(list);
        tripService.findByDriver(new Driver());
        verify(tripRepository, times(1)).findByDriver(any(Driver.class));
    }

    @Test
    public void findEmptyTripsListByDriverTest() {
        given(tripRepository.findByDriver(any(Driver.class))).willReturn(new ArrayList<>());
        tripService.findByDriver(new Driver());
        verify(tripRepository, times(1)).findByDriver(any(Driver.class));
    }

    @Test
    public void findTripsByCarTest() {
        List<Trip> list = new ArrayList<>();
        list.add(new Trip());
        given(tripRepository.findByCar(anyString())).willReturn(list);
        tripService.findByCar("VOLVO");
        verify(tripRepository, times(1)).findByCar(anyString());
    }

    @Test(expected = LogisticException.class)
    public void findEmptyTripsListByCarTest() {
        given(tripRepository.findByCar(anyString())).willReturn(new ArrayList<>());
        tripService.findByCar("VOLVO");
        verify(tripRepository, times(1)).findByCar(anyString());
    }

    @Test
    public void findTripsByPointsTest() {
        List<Trip> list = new ArrayList<>();
        list.add(new Trip(new Driver("Mike", 40),"VOLVO",15.0d,"Brest","Minsk","350",
                Date.valueOf("2016-07-14"),Date.valueOf("2016-07-15")));
        given(tripRepository.findByStartPointAndEndPoint(anyString(), anyString())).willReturn(list);
        tripService.findByStartPointAndEndPoint("Brest", "Minsk");
        verify(tripRepository, times(1)).findByStartPointAndEndPoint(anyString(), anyString());
    }

    @Test(expected = LogisticException.class)
    public void findEmptyTripsListByPointsTest() {
        given(tripRepository.findByStartPointAndEndPoint(anyString(), anyString())).willReturn(new ArrayList<>());
        tripService.findByStartPointAndEndPoint("Brest", "Minsk");
        verify(tripRepository, times(1)).findByStartPointAndEndPoint(anyString(), anyString());
    }

    @Test
    public void findTripsByDatesTest() {
        List<Trip> list = new ArrayList<>();
        list.add(new Trip());
        given(tripRepository.findByStartDateBetweenAndEndDateBetween(
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"),
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25")))
                .willReturn(list);
        tripService.findByStartDateAndEndDate(Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"));
        verify(tripRepository, times(1)).findByStartDateBetweenAndEndDateBetween(
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"),
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"));
    }

    @Test(expected = LogisticException.class)
    public void findEmptyTripsListByDatesTest() {
        given(tripRepository.findByStartDateBetweenAndEndDateBetween(
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"),
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25")))
                .willReturn(new ArrayList<>());
        tripService.findByStartDateAndEndDate(Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"));
        verify(tripRepository, times(1)).findByStartDateBetweenAndEndDateBetween(
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"),
                Date.valueOf("2017-02-24"), Date.valueOf("2017-02-25"));
    }

}
