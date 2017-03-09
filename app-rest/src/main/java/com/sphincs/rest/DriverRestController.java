package com.sphincs.rest;

import com.sphincs.domain.Driver;
import com.sphincs.domain.RepresentationDriver;
import com.sphincs.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/drivers")
public class DriverRestController {

    private final DriverService driverService;

    @Autowired
    public DriverRestController(DriverService driverService) {
        this.driverService = driverService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addDriver(@Valid @RequestBody final Driver driver) {
        Driver savedDriver = driverService.save(driver);
        return new ResponseEntity(savedDriver.getId(), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ok(driverService.findAllDrivers());
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeDriver(@PathVariable Long id) {
        driverService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return ok(driverService.findDriverById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Driver> getDriverByName(@PathVariable String name) {
        return ok(driverService.findByName(name));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateDriver(@Valid @RequestBody final Driver driver) {
        return ok(driverService.save(driver));
    }

}
