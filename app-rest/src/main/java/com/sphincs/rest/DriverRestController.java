package com.sphincs.rest;

import com.sphincs.domain.Car;
import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverRestController {

    @Autowired
    private DriverService driverService;
/*
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addDriver(@RequestBody Driver driver) {
        Long id = driverService.addDriver(driver);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    public ResponseEntity <List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return new ResponseEntity(drivers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/id/{/id}", method = RequestMethod.DELETE)
    public ResponseEntity removeDriver(@PathVariable Long id) {
        driverService.removeDriver(id);
        return new ResponseEntity("", HttpStatus.OK);
    }
*/
    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        try {
            Driver driver = driverService.getDriverById(id);
            return new ResponseEntity(driver, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Driver with id = " + id + " not found. Error: "
                            + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverByName(@PathVariable String name) {
        try {
            Driver driver = driverService.getDriverByName(name);
            return new ResponseEntity(driver, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Driver with name = " + name + " not found. Error: "
                    + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/car/{/car}", method = RequestMethod.GET)
    public ResponseEntity<List<Driver>> getDriversByCar(Car car) {
        try {
            List<Driver> drivers = driverService.getDriversByCar(car);
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Driver with car = " + car + " not found. Error: "
                    + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
/*
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateDriver(Driver driver) {
        driverService.updateDriver(driver);
        return new ResponseEntity("", HttpStatus.OK);
    }

*/


}
