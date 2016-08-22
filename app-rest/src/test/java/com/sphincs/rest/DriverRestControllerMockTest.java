package com.sphincs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
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

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ContextConfiguration(locations = {"classpath:/spring-rest-mock-test.xml"})
public class DriverRestControllerMockTest extends AbstractTestNGSpringContextTests {

    private MockMvc mockMvc;

    @Resource
    private DriverRestController driverRestController;

    @Autowired
    private DriverService driverService;

    @BeforeClass
    public void setUp() {
        this.mockMvc = standaloneSetup(driverRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @AfterMethod
    public void clean() {
        reset(driverService);
    }

    @Test
    public void addDriverTest() throws Exception {
        expect(driverService.addDriver(anyObject(Driver.class)))
                .andReturn(1L);
        replay(driverService);
        ObjectMapper objectMapper = new ObjectMapper();
        String driverJson = objectMapper.writeValueAsString(DriverDataFixture.getNewDriver());

        this.mockMvc.perform(
                post("/drivers")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
        verify(driverService);
    }

    @Test
    public void addBadDriverTest() throws Exception {
        expect(driverService.addDriver(DriverDataFixture.getBadNewDriver()))
                .andReturn(null);
        replay(driverService);
        ObjectMapper objectMapper = new ObjectMapper();
        String driverJson = objectMapper.writeValueAsString(DriverDataFixture.getBadNewDriver());

        this.mockMvc.perform(
                post("/drivers")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(driverService);
    }

    @Test
    public void addExistDriverTest() throws Exception {
        expect(driverService.addDriver(anyObject(Driver.class)))
                .andThrow(new IllegalArgumentException());
        replay(driverService);
        ObjectMapper objectMapper = new ObjectMapper();
        String driverJson = objectMapper.writeValueAsString(DriverDataFixture.getNewDriver());

        this.mockMvc.perform(
                post("/drivers")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
        verify(driverService);
    }

    @Test
    public void getAllDriversTest() throws Exception {
        expect(driverService.getAllDrivers())
                .andReturn(DriverDataFixture.getAllDrivers());
        replay(driverService);
        this.mockMvc.perform(
                get("/drivers/all/").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"Mike\",\"age\":35},"
                        + "{\"id\":2,\"name\":\"Bobby\",\"age\":33}]"));
        verify(driverService);
    }

    @Test
    public void getDriverByIdTest() throws Exception {
        Long driverId = 1L;
        expect(driverService.getDriverById(driverId))
                .andReturn(DriverDataFixture.getExistingDriver(driverId));
        replay(driverService);
        this.mockMvc.perform(
                get("/drivers/id/" + driverId).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + driverId + ",\"name\":\"Mike\",\"age\":35}"));
        verify(driverService);
    }

    @Test
    public void getNotFoundDriverByIdTest() throws Exception {
        expect(driverService.getDriverById(10L)).andReturn(null);
        replay(driverService);

        this.mockMvc.perform(
                get("/drivers/id/10").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(driverService);
    }

    @Test
    public void getDriverByNameTest() throws Exception {
        String driverName = "Mike";
        expect(driverService.getDriverByName(driverName))
                .andReturn(DriverDataFixture.getExistingDriverByName(driverName));
        replay(driverService);
        this.mockMvc.perform(
                get("/drivers/name/" + driverName).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"name\":\"" + driverName + "\",\"age\":33}"));
        verify(driverService);
    }

    @Test
    public void getNotFoundDriverByNameTest() throws Exception {
        String driverName = "Rick";
        expect(driverService.getDriverByName(driverName))
                .andReturn(null);
        replay(driverService);
        this.mockMvc.perform(
                get("/drivers/name/" + driverName).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(driverService);
    }

    @Test
    public void removeDriverTest() throws Exception {
        driverService.removeDriver(1L);
        expectLastCall();
        replay(driverService);
        ResultActions result = this.mockMvc.perform(
                delete("/drivers/remove/1"))
                .andDo(print());
        result.andExpect(status().isOk());
        verify(driverService);
    }

    @Test
    public void updateDriverTest() throws Exception {
        driverService.updateDriver(anyObject(Driver.class));
        expectLastCall();
        replay(driverService);

        ObjectMapper objectMapper = new ObjectMapper();
        Driver driver = DriverDataFixture.getExistingDriver(1L);
        driver.setAge(18);
        String driverJson = objectMapper.writeValueAsString(driver);

        ResultActions result = this.mockMvc.perform(
                put("/drivers")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        result.andExpect(status().isOk());
        verify(driverService);
    }

}
