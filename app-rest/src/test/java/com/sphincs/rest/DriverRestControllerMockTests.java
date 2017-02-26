package com.sphincs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
import com.sphincs.service.LogisticException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DriverRestControllerMockTests {

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected DriverService driverService;
    @Autowired
    protected DriverRestController driverRestController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        driverRestController = new DriverRestController(driverService);
        this.mockMvc = standaloneSetup(driverRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void addDriverTest() throws Exception {
        Driver driver = DriverDataFixture.getNewDriver();
        driver.setId(1L);
        given(driverService.save(any(Driver.class))).willReturn(driver);

        String driverJson = objectMapper.writeValueAsString(driver);

        mockMvc.perform(
                post("/drivers")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(driverService, times(1)).save(any(Driver.class));
    }

    @Test
    public void addBadDriverTest() throws Exception {
        given(driverService.save(any(Driver.class))).willReturn(null);

        String driverJson = objectMapper.writeValueAsString(DriverDataFixture.getBadNewDriver());

        mockMvc.perform(
                post("/drivers")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(driverService, never()).save(any(Driver.class));
    }

    @Test
    public void getAllDrivers() throws Exception {
        given(driverService.findAll()).willReturn(DriverDataFixture.getAllDrivers());

        mockMvc.perform(
                get("/drivers/")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"Mike\",\"age\":35},"
                        + "{\"id\":2,\"name\":\"Bobby\",\"age\":33}]"));

        verify(driverService, times(1)).findAll();

    }

    @Test
    public void removeDriver() throws Exception {
        mockMvc.perform(
                delete("/drivers/1")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getDriverById() throws Exception {
        given(driverService.findById(1L)).willReturn(DriverDataFixture.getExistingDriver(1L));

        mockMvc.perform(
                get("/drivers/1").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Mike\",\"age\":35}"));

        verify(driverService, times(1)).findById(1L);
    }

    @Test
    public void getDriverByName() throws Exception {
        given(driverService.findByName("Mike")).willReturn(DriverDataFixture.getExistingDriverByName("Mike"));

        mockMvc.perform(
                get("/drivers/name/Mike").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"name\":\"Mike\",\"age\":33}"));

        verify(driverService, times(1)).findByName("Mike");
    }

}
