package com.sphincs.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;

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

    @AfterClass
    public void clean() throws Exception {
        reset(driverService);
    }

    @Test
    public void getDriverByIdTest() throws Exception {
        Long driverId = 1L;
        expect(driverService.getDriverById(driverId))
                .andReturn(DriverDataFixture.getExistingDriver(driverId));
        replay(driverService);
        this.mockMvc.perform(
                get("/drivers/id/1").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string("{\"id\":1, \"name\":\"Ralph\", \"age\":33, \"categories\":}"))
        verify(driverService);
    }


    /*
    @Test
    public void addDriverTest() throws Exception {
        driverService.addDriver(anyObject(Driver.class));
        expectLastCall().andReturn(1L);

        replay(driverService);

        ObjectMapper objectMapper = new ObjectMapper();
        String driverJson = objectMapper.writeValueAsString(DriverDataFixture.getNewDriver());

        this.mockMvc.perform(
                post("/driver")
                    .content(driverJson)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(driverService);
    }*/
/*
    @Test
    public void getAllDriversTest() {

    }

    @Test
    public void removeDriverTest() {

    }



    @Test
    public void getDriverByNameTest() {

    }

    @Test
    public void getDriversByCarTest() {

    }

    @Test
    public void updateDriverTest() {

    }

*/

}
