package com.sphincs.web;

import com.sphincs.domain.Driver;
import com.sphincs.service.DriverService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private static final Logger LOGGER = LogManager.getLogger();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
    public ModelAndView getInputFormView(@RequestParam("Name") String driverName,
                                         @RequestParam("Age") String driverAge,
                                         @RequestParam("Category") String driverCategories,
                                         @RequestParam("Car") String driverCar,
                                         @RequestParam("Number") String carNumber) {
        LOGGER.debug("add new driver");
        Driver driver = checkDriverFields(new Driver(), driverName, driverAge, driverCategories, driverCar, carNumber);
        if (driver != null) {
            driverService.addDriver(driver);
            return getDriversListView();
        } else {
            return getErrorPage("Введены некорректные данные. Проверьте правильность ввода");
        }
    }

    @RequestMapping(value = "/driverRemoveForm")
    public ModelAndView removeDriverFormView() {
        LOGGER.debug("get all available drivers");
        List<Driver> drivers = driverService.getAllDrivers();
        return new ModelAndView("removeDriver", "drivers", drivers);
    }

    @RequestMapping(value = "/driverRemove")
    public ModelAndView removeDriverView(@RequestParam("Id") Long driverId) {
        LOGGER.debug("Delete driver with id = " + driverId);
        Driver driver = driverService.getDriverById(driverId);
        if (driver != null) {
            driverService.removeDriver(driverId);
            return getDriversListView();
        } else {
            return getErrorPage("Водитель с ID = " + driverId + " не существует");
        }
    }

    @RequestMapping(value = "/driverUpdateId")
    public ModelAndView updateDriverFormView() {
        LOGGER.debug("get all available drivers");
        List<Driver> drivers = driverService.getAllDrivers();
        return new ModelAndView("updateDriver", "drivers", drivers);
    }

    @RequestMapping(value = "/driverUpdateForm")
    public ModelAndView getDriverByIdView(@RequestParam("Id") Long driverId) {
        LOGGER.debug("drivers.id = " + driverId);
        Driver driver = driverService.getDriverById(driverId);
        if (driver != null) {
            return new ModelAndView("updateDriverForm", "driver", driver);
        } else {
            return getErrorPage("Водитель с ID = " + driverId + " не существует");
        }
    }

    @RequestMapping(value = "/driverUpdate")
    public ModelAndView updateDriver(@RequestParam("Id") Long driverId,
                                     @RequestParam("Name") String driverName,
                                     @RequestParam("Age") String driverAge,
                                     @RequestParam("Category") String driverCategories,
                                     @RequestParam("Car") String driverCar,
                                     @RequestParam("Number") String carNumber) {
        LOGGER.debug("update driver with id = " + driverId);
        Driver driver = driverService.getDriverById(driverId);
        driver = checkDriverFields(driver, driverName, driverAge, driverCategories, driverCar, carNumber);
        if (driver != null) {
            driverService.updateDriver(driver);
            return getDriversListView();
        } else {
            return getErrorPage("Введены некорректные данные. Проверьте правильность ввода");
        }
    }

    private Driver checkDriverFields(Driver driver, String driverName, String driverAge, String driverCategories, String driverCar,
                                     String carNumber) {
        boolean errorStatus = false;
        Pattern isNumber = Pattern.compile("[0-9]+");

        if (!errorStatus && driverName.length() > 0 && !isNumber.matcher(driverName).matches())
            driver.setName(driverName);
        else errorStatus = true;

        try {
            if (!errorStatus && isNumber.matcher(driverAge).matches() && Integer.parseInt(driverAge) > 18)
                driver.setAge(Integer.parseInt(driverAge));
            else errorStatus = true;
        } catch (NumberFormatException e) {
            errorStatus = true;
        }

        if (!errorStatus && driverCategories.charAt(0) == '[' && driverCategories.charAt(driverCategories.length() - 1) == ']'
                && !isNumber.matcher(driverCategories).matches())
            driver.setCategories(driver.getCategoriesFromString(driverCategories));
        if (driver.getCategories() == null) errorStatus = true;

        if (!errorStatus) driver.setCar(driver.getCarFromString(driverCar));
        if (driver.getCar() == null) errorStatus = true;

        Pattern carNumberPat = Pattern.compile("^\\d{4}-[a-zA-Z]{2}\\d$");
        if (!errorStatus
                && (driverService.getDriverByCarNumber(carNumber) == null || (driver.getCarNumber() != null
                && driverService.getDriverByCarNumber(carNumber).getId() == driver.getId())
                && carNumberPat.matcher(carNumber).matches()))
            driver.setCarNumber(carNumber);
        else errorStatus = true;

        if (!errorStatus) {
            driver.setFuelRate100();
            return driver;
        } else return null;
    }

    private ModelAndView getErrorPage(String exceptionMessage) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("datetime", format.format(new Date().getTime()));
        errorMap.put("exception", exceptionMessage);
        errorMap.put("url", "/drivers/driverInputForm");
        return new ModelAndView("error/404", "errorMap", errorMap);
    }
}
