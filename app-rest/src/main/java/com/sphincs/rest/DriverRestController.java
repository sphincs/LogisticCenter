package com.sphincs.rest;

import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverRestController {

    @Autowired
    private DriverService driverService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addDriver(@Valid @RequestBody final Driver driver, final BindingResult result) {
        if (result.hasErrors()) return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        try {
            driverService.save(driver);
            return new ResponseEntity("{}", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.findAll();
        return new ResponseEntity(drivers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeDriver(@PathVariable Long id) {
        driverService.delete(id);
        return new ResponseEntity("{}", HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        Driver driver = driverService.findOne(id);
        if (driver == null) {
            return new ResponseEntity("Driver with id = " + id + " not found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(driver, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverByName(@PathVariable String name) {
        Driver driver = driverService.findByName(name);
        if (driver == null) {
            return new ResponseEntity("Driver with name = " + name + " not found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(driver, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateDriver(@Valid @RequestBody final Driver driver, final BindingResult result) {
        if (result.hasErrors()) return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        try {
            driverService.save(driver);
            return new ResponseEntity("{}", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
