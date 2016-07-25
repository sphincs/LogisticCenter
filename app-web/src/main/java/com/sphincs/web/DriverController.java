package com.sphincs.web;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.DriverService;
import com.sphincs.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private static final Logger LOGGER = LogManager.getLogger();
    //public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //private Driver managedDriver = new Driver();


    @Autowired
    private DriverService driverService;

    @RequestMapping("/")
    public String init() {
        return "hello";
    }

/*
    @RequestMapping(value = "/driverslist")
    public ModelAndView getDriversListView() {
        List<Driver> drivers = driverService.getAllDrivers();
        LOGGER.debug("drivers.size = " + drivers.size());
        ModelAndView view = new ModelAndView("driversList", "drivers", drivers);
        return view;
    }

*/





















}
