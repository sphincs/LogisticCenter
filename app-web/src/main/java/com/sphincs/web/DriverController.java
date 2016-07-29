package com.sphincs.web;

import com.sphincs.domain.Car;
import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private DriverService driverService;

    @RequestMapping("/")
    public String init() {
        return "redirect:/drivers/driversList";
    }

    @RequestMapping(value = "/driversList")
    public ModelAndView getDriversListView() {
        List<Driver> drivers = driverService.getAllDrivers();
        LOGGER.debug("drivers.size = " + drivers.size());
        return new ModelAndView("driversList", "drivers", drivers);
    }

    @RequestMapping("/driverInputForm")
    public ModelAndView launchDriverInputFormView() {
        return new ModelAndView("driverInputForm", "driver", new Driver());
    }

    @RequestMapping("/submitData")
    public String getInputFormView(@RequestParam("Name") String driverName,
                                   @RequestParam("Age") Integer driverAge,
                                   @RequestParam("Category") String driverCategories,
                                   @RequestParam("Car") String driverCar,
                                   @RequestParam("Number") String carNumber) {
        Driver driver = new Driver();
        driver.setName(driverName);
        driver.setAge(driverAge);
        driver.setCategories(driver.getCategoriesFromString(driverCategories));
        driver.setCar(Car.valueOf(driverCar));
        driver.setCarNumber(carNumber);
        driver.setFuelRate100();
        driverService.addDriver(driver);
        return "redirect:/drivers/driversList";
    }

    @RequestMapping(value = "/driverRemoveForm")
    public ModelAndView removeDriverFormView() {
        LOGGER.debug("get all available drivers");
        List<Driver> drivers = driverService.getAllDrivers();
        return new ModelAndView("removeDriver", "drivers", drivers);
    }

    @RequestMapping(value = "/driverRemove")
    public String removeDriverView(@RequestParam("Id") Long driverId) {
        LOGGER.debug("Delete driver with id = " + driverId);
        driverService.removeDriver(driverId);
        return "redirect:/drivers/driversList";
    }

    @RequestMapping(value = "/driverUpdateId")
    public ModelAndView updateDriverFormView() {
        LOGGER.debug("get all available drivers");
        List<Driver> drivers = driverService.getAllDrivers();
        return new ModelAndView("updateDriver", "drivers", drivers);
    }

    @RequestMapping(value = "/driverUpdateForm")
    public ModelAndView getDriverByIdView(@RequestParam("Id") Long driverId) {
        Driver driver = driverService.getDriverById(driverId);
        LOGGER.debug("drivers.id = " + driverId);
        return new ModelAndView("updateDriverForm", "driver", driver);
    }

    @RequestMapping(value = "/driverUpdate")
    public String updateDriver(@RequestParam("Id") Long driverId,
                               @RequestParam("Name") String driverName,
                               @RequestParam("Age") Integer driverAge,
                               @RequestParam("Category") String driverCategories,
                               @RequestParam("Car") String driverCar,
                               @RequestParam("Number") String carNumber) {
        Driver driver = driverService.getDriverById(driverId);
        driver.setName(driverName);
        driver.setAge(driverAge);
        driver.setCategories(driver.getCategoriesFromString(driverCategories));
        driver.setCar(Car.valueOf(driverCar));
        driver.setCarNumber(carNumber);
        driver.setFuelRate100();
        LOGGER.debug("update driver with id = " + driverId);
        driverService.updateDriver(driver);
        return "redirect:/drivers/driversList";
    }
}
