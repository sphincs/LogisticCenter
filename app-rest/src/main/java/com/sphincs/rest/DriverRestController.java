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

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addDriver(@RequestBody Driver driver) {
        try {
            Long driverId = driverService.addDriver(driver);
            if (driverId == null) {
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(driverId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    public ResponseEntity <List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return new ResponseEntity(drivers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeDriver(@PathVariable Long id) {
        driverService.removeDriver(id);
        return new ResponseEntity("", HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        Driver driver = driverService.getDriverById(id);
        if (driver == null) {
            return new ResponseEntity("Driver with id = " + id + " not found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(driver, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverByName(@PathVariable String name) {
        Driver driver = driverService.getDriverByName(name);
        if (driver == null) {
            return new ResponseEntity("Driver with name = " + name + " not found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(driver, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/car/{car}", method = RequestMethod.GET)
    public ResponseEntity<List<Driver>> getDriversByCar(@PathVariable Car car) {
        List<Driver> drivers = driverService.getDriversByCar(car);
        if (drivers == null) {
            return new ResponseEntity("Drivers with car = " + car + " not found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(drivers, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateDriver(@RequestBody Driver driver) {
        try {
            driverService.updateDriver(driver);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
