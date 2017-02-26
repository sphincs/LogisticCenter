package com.sphincs.service;

import com.sphincs.dao.DriverRepository;
import com.sphincs.domain.Driver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverServiceMockTests {

    @MockBean
    protected DriverRepository driverRepository;
    @Autowired
    protected DriverService driverService;

    @Before
    public void setUp() {
        driverService = new DriverServiceImpl(driverRepository);
    }

    @Test
    public void driverSaveTest() {
        Driver driver = new Driver("Mike", 40);
        given(driverRepository.save(driver)).willReturn(driver);
        driverService.save(driver);
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    public void driverFindAllTest() {
        given(driverRepository.findAll()).willReturn(new ArrayList<Driver>());
        driverService.findAll();
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    public void driverFindByIdTest() {
        given(driverRepository.findById(1L)).willReturn(Optional.of(new Driver()));
        driverService.findById(1L);
        verify(driverRepository, times(1)).findById(1L);
    }

    @Test
    public void driverCountTest() {
        given(driverRepository.count()).willReturn(1L);
        driverService.count();
        verify(driverRepository, times(1)).count();
    }

    @Test
    public void driverFindByNameTest() {
        given(driverRepository.findByName("Mike")).willReturn(Optional.of(new Driver()));
        driverService.findByName("Mike");
        verify(driverRepository, times(1)).findByName("Mike");
    }

    @Test
    public void driverDeleteTest() {
        driverService.delete(1L);
        verify(driverRepository, times(1)).delete(1L);
    }

}
