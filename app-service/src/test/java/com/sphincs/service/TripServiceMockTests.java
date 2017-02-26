package com.sphincs.service;

import com.google.common.collect.Lists;
import com.sphincs.dao.TripRepository;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TripServiceMockTests {

    @MockBean
    protected TripRepository tripRepository;
    @MockBean
    protected DriverService driverService;
    @Autowired
    protected TripService tripService;

    @Before
    public void setUp() {
        tripService = new TripServiceImpl(driverService, tripRepository);
    }

    @Test
    public void addTripTest() {
        Trip trip = new Trip("Mike","VOLVO",15.0d,"Brest","Minsk","350",
                Date.valueOf("2016-07-14"),Date.valueOf("2016-07-15"));
        List<Trip> list = Lists.newArrayList(trip);
        given(tripRepository.save(trip)).willReturn(trip);
        given(tripRepository.findByDriverName(anyString())).willReturn(list);
        given(driverService.findByName(anyString())).willReturn(new Driver("Mike", 40));
        tripService.save(trip);
        verify(tripRepository, times(1)).save(trip);
        verify(driverService, times(1)).findByName(anyString());
    }

    @Test
    public void findAllTripsTest() {
        given(tripRepository.findAll()).willReturn(new ArrayList<Trip>());
        tripService.findAll();
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    public void findTripByIdTest() {
        given(tripRepository.findById(1L)).willReturn(Optional.of(new Trip()));
        tripService.findById(1L);
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
    public void findTripsByDriverNameTest() {
        List<Trip> list = new ArrayList<>();
        list.add(new Trip());
        given(tripRepository.findByDriverName(anyString())).willReturn(list);
        tripService.findByDriverName("Mike");
        verify(tripRepository, times(1)).findByDriverName(anyString());
    }

    @Test
    public void findEmptyTripsListByDriverNameTest() {
        given(tripRepository.findByDriverName(anyString())).willReturn(new ArrayList<>());
        tripService.findByDriverName("Mike");
        verify(tripRepository, times(1)).findByDriverName(anyString());
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
        list.add(new Trip());
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
