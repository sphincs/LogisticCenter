package com.sphincs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.DriverService;
import com.sphincs.service.TripService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TripRestControllerMockTests {

    @Autowired
    protected MockMvc mockMvc;
    @Mock
    protected DriverService driverService;
    @Mock
    protected TripService tripService;
    @Autowired
    protected TripRestController tripRestController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        tripRestController = new TripRestController(driverService, tripService);
        this.mockMvc = standaloneSetup(tripRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void addTripTest() throws Exception {
        Trip trip = TripDataFixture.getNewTripWithId(1L);
        given(tripService.save(any(Trip.class))).willReturn(trip);

        String tripJson = objectMapper.writeValueAsString(trip);

        mockMvc.perform(
                post("/trips")
                        .content(tripJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(tripService, times(1)).save(any(Trip.class));
    }

    @Test
    public void getAllTripsTest() throws Exception {
        given(tripService.findAllTrips()).willReturn(TripDataFixture.getAllTrips());

        mockMvc.perform(
                get("/trips/")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"driverId\":null,"
                + "\"car\":\"VOLVO\",\"fuelRate100\":15.0,\"startPoint\":\"Gomel\",\"endPoint\":\"Rome\",\"distance\":\"2530\","
                + "\"startDate\":\"2016-07-05\",\"endDate\":\"2016-07-08\",\"sumFuel\":\"379,50\"},"
                + "{\"id\":2,\"driverId\":null,\"car\":\"VOLVO\","
                + "\"fuelRate100\":15.0,\"startPoint\":\"Gomel\",\"endPoint\":\"Rome\",\"distance\":\"2530\","
                + "\"startDate\":\"2016-07-05\",\"endDate\":\"2016-07-08\",\"sumFuel\":\"379,50\"}]"));

        verify(tripService, times(1)).findAllTrips();
    }

    @Test
    public void getTripByIdTest() throws Exception {
        given(tripService.findTripById(anyLong())).willReturn(TripDataFixture.getTripByRoute(1L));

        mockMvc.perform(
                get("/trips/id/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,"
                        + "\"driverId\":null,\"car\":\"VOLVO\","
                        + "\"fuelRate100\":15.0,\"startPoint\":\"Brest\",\"endPoint\":\"Minsk\",\"distance\":\"350\","
                        + "\"startDate\":\"2016-07-14\",\"endDate\":\"2016-07-15\",\"sumFuel\":\"52,50\"}"));

        verify(tripService, times(1)).findTripById(anyLong());
    }

    @Test
    public void getTripsByDriverTest() throws Exception {
        given(tripService.findByDriver(any(Driver.class))).willReturn(TripDataFixture.getTripsByDriver(anyString()));

        mockMvc.perform(
                get("/trips/driver/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(tripService, times(1)).findByDriver(any(Driver.class));
    }

    @Test
    public void getTripsByRouteTest() throws Exception {
        given(tripService.findByStartPointAndEndPoint(anyString(), anyString()))
                .willReturn(TripDataFixture.getTripsByRoute());

        mockMvc.perform(
                get("/trips/route/Mexico/Chicago")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(tripService, times(1)).findByStartPointAndEndPoint(anyString(), anyString());
    }

    @Test
    public void getTripsByCarTest() throws Exception {
        given(tripService.findByCar(anyString())).willReturn(TripDataFixture.getTripsByDriver(anyString()));

        mockMvc.perform(
                get("/trips/car/VOLVO")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(tripService, times(1)).findByCar(anyString());
    }

    @Test
    public void removeTripTest() throws Exception {
        mockMvc.perform(
                delete("/trips/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateTripTest() throws Exception {
        Trip trip = TripDataFixture.getNewTrip();
        given(tripService.save(any(Trip.class))).willReturn(trip);
        given(driverService.findByName(anyString())).willReturn(new Driver());

        String tripJson = objectMapper.writeValueAsString(trip);

        mockMvc.perform(
                put("/trips")
                        .content(tripJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(tripService, times(1)).save(any(Trip.class));
    }

    @Test
    public void countFuelByDriverTest() throws Exception {
        given(tripService.countFuelByDriver(anyString())).willReturn(new HashMap<>());

        mockMvc.perform(
                get("/trips/sumFuel/driver/Mike")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(tripService, times(1)).countFuelByDriver(anyString());
    }

    @Test
    public void countFuelByDatesTest() throws Exception {
        given(tripService.countFuelByDate("2016-07-14", "2016-07-15")).willReturn(new HashMap<>());

        mockMvc.perform(
                get("/trips/sumFuel/date/2016-07-14/2016-07-15")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(tripService, times(1)).countFuelByDate("2016-07-14", "2016-07-15");
    }

}
