package com.sphincs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphincs.domain.Trip;
import com.sphincs.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ContextConfiguration(locations = "classpath:/spring-rest-mock-test.xml")
public class TripRestControllerMockTest extends AbstractTestNGSpringContextTests {

    private MockMvc mockMvc;

    @Resource
    private TripRestController tripRestController;

    @Autowired
    private TripService tripService;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeClass
    public void setUp() {
        this.mockMvc = standaloneSetup(tripRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @AfterMethod
    public void clean() {
        reset(tripService);
    }

    @Test
    public void addTripTest() throws Exception {
        expect(tripService.addTrip(anyObject(Trip.class)))
                .andReturn(1L);
        replay(tripService);
        ObjectMapper objectMapper = new ObjectMapper();
        String tripJson = objectMapper.writeValueAsString(TripDataFixture.getNewTrip());

        this.mockMvc.perform(
                post("/trips")
                        .content(tripJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
        verify(tripService);
    }

    @Test
    public void addBadTripTest() throws Exception {
        expect(tripService.addTrip(TripDataFixture.getBadNewTrip()))
                .andReturn(null);
        replay(tripService);
        ObjectMapper objectMapper = new ObjectMapper();
        String tripJson = objectMapper.writeValueAsString(TripDataFixture.getBadNewTrip());

        this.mockMvc.perform(
                post("/trips")
                        .content(tripJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(tripService);
    }

    @Test
    public void addExistTripTest() throws Exception {
        expect(tripService.addTrip(anyObject(Trip.class)))
                .andThrow(new IllegalArgumentException());
        replay(tripService);
        ObjectMapper objectMapper = new ObjectMapper();
        String tripJson = objectMapper.writeValueAsString(TripDataFixture.getNewTrip());

        this.mockMvc.perform(
                post("/trips")
                        .content(tripJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
        verify(tripService);
    }

    @Test
    public void getAllTripsTest() throws Exception {
        expect(tripService.getAllTrips())
                .andReturn(TripDataFixture.getAllTrips());
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/all").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(tripService);
    }

    @Test
    public void getTripByIdTest() throws Exception {
        Long tripId = 1L;
        expect(tripService.getTripById(tripId))
                .andReturn(TripDataFixture.getNewTripWithId(tripId));
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/id/" + tripId).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"driverName\":\"Mike\",\"car\":\"VOLVO\",\"fuelRate100\":15.0," +
                        "\"startPoint\":\"Gomel\",\"endPoint\":\"Rome\",\"distance\":\"2530\"," +
                        "\"startDate\":1467666000000,\"endDate\":1467925200000,\"sumFuel\":\"379,50\"}"));
        verify(tripService);
    }

    @Test
    public void getNotFoundTripByIdTest() throws Exception {
        Long tripId = 8L;
        expect(tripService.getTripById(tripId))
                .andReturn(null);
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/id/" + tripId).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(tripService);
    }

    @Test
    public void getTripsByDriverTest() throws Exception {
        String driverName = "Ralph";
        expect(tripService.getTripsByDriver(driverName))
                .andReturn(TripDataFixture.getTripsByDriver(driverName));
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/driver/" + driverName).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(tripService);
    }

    @Test
    public void getNotFoundTripsByDriverTest() throws Exception {
        String driverName = "Ralph";
        expect(tripService.getTripsByDriver(driverName))
                .andReturn(null);
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/driver/" + driverName).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(tripService);
    }

    @Test
    public void getTripsByRouteTest() throws Exception {
        String startPoint = "Brest";
        String endPoint = "Minsk";
        expect(tripService.getTripsByRoute(startPoint, endPoint))
                .andReturn(TripDataFixture.getTripsByRoute());
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/route/Brest/Minsk").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(tripService);
    }

    @Test
    public void getNotFoundTripsByRouteTest() throws Exception {
        String startPoint = "Brest";
        String endPoint = "Minsk";
        expect(tripService.getTripsByRoute(startPoint, endPoint))
                .andReturn(null);
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/route/Brest/Minsk").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(tripService);
    }

    @Test
    public void getTripsByDateTest() throws Exception {
        List<Trip> trips = TripDataFixture.getTripsByDate();
        String startDate = "2016-07-05";
        String endDate = "2016-07-08";
        Date start = formatter.parse(startDate);
        Date end = formatter.parse(endDate);
        expect(tripService.getTripsByDate(start, end))
                .andReturn(trips);
        replay(tripService);
        this.mockMvc.perform(
                get(String.format("/trips/date/%s/%s", startDate, endDate)).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(tripService);
    }

    @Test
    public void getTripsByCarTest() throws Exception {
        String car = "VOLVO";
        expect(tripService.getTripsByCar(car))
                .andReturn(TripDataFixture.getTripsByCar());
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/car/" + car).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(tripService);
    }

    @Test
    public void getNotFoundTripsByCarTest() throws Exception {
        String car = "MAN";
        expect(tripService.getTripsByCar(car))
                .andReturn(null);
        replay(tripService);
        this.mockMvc.perform(
                get("/trips/car/" + car).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(tripService);
    }

    @Test
    public void removeTripTest() throws Exception {
        tripService.removeTrip(1L);
        expectLastCall();
        replay(tripService);
        ResultActions result = this.mockMvc.perform(
                delete("/trips/remove/1"))
                .andDo(print());
        result.andExpect(status().isOk());
        verify(tripService);
    }

    @Test
    public void updateTripTest() throws Exception {
        tripService.updateTrip(anyObject(Trip.class));
        expectLastCall();
        replay(tripService);

        ObjectMapper objectMapper = new ObjectMapper();
        Trip trip = TripDataFixture.getNewTripWithId(1L);
        trip.setStartPoint("Vitebsk");
        String tripJson = objectMapper.writeValueAsString(trip);

        ResultActions result = this.mockMvc.perform(
                put("/trips")
                        .content(tripJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        result.andExpect(status().isOk());
        verify(tripService);
    }

}
