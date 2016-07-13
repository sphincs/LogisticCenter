package com.sphincs.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sphincs.domain.Car;
import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.TestException;
import org.testng.annotations.*;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
                .andExpect(content().string("{\"id\":" + driverId + ",\"name\":\"Ralph\",\"age\":33,\"categories\":[\"D\"],\"car\":\"DAF\",\"carNumber\":\"1234-ab1\",\"fuelRate100\":13.5}"));
        verify(driverService);
    }

    @Test
    public void getNotFoundDriverByIdTest() throws Exception{
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
                .andExpect(content().string("{\"id\":2,\"name\":\"" + driverName + "\",\"age\":40,\"categories\":[\"B\"],\"car\":\"BMW\",\"carNumber\":\"9876-ab1\",\"fuelRate100\":7.5}"));
        verify(driverService);
    }


    @Test
    public void getDriversByCarTest() throws Exception {
        Car car = Car.DAF;
        expect(driverService.getDriversByCar(car))
                .andReturn(DriverDataFixture.getExistingDriversByCar(car));
        replay(driverService);
        this.mockMvc.perform(
                get("/drivers/car/" + car).accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"Ralph\",\"age\":33,\"categories\":[\"D\"],\"car\":\"DAF\",\"carNumber\":\"1234-ab1\",\"fuelRate100\":13.5},"
                        + "{\"id\":2,\"name\":\"Ralph\",\"age\":33,\"categories\":[\"D\"],\"car\":\"DAF\",\"carNumber\":\"1234-ab1\",\"fuelRate100\":13.5}]"));
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
                .andExpect(content().string("[{\"id\":1,\"name\":\"Ralph\",\"age\":33,\"categories\":[\"D\"],\"car\":\"DAF\",\"carNumber\":\"1234-ab1\",\"fuelRate100\":13.5},"
                        + "{\"id\":2,\"name\":\"Mike\",\"age\":40,\"categories\":[\"B\"],\"car\":\"BMW\",\"carNumber\":\"9876-ab1\",\"fuelRate100\":7.5}]"));
        verify(driverService);
    }

    @Test
    public void addDriverTest() throws Exception {
        expect(driverService.addDriver(DriverDataFixture.getNewDriver()))
                .andReturn(1L);
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
                .andExpect(status().isCreated());
               // .andExpect(content().string("1"));
        verify(driverService);
    }
/*


    @Test
    public void removeDriverTest() {

    }




    @Test
    public void updateDriverTest() {

    }

*/

}
