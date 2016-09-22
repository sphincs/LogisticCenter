package com.sphincs.web;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.DriverService;
import com.sphincs.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class TripController {

    private static final Logger LOGGER = LogManager.getLogger();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private DriverService driverService;

    @Autowired
    private TripService tripService;

    @RequestMapping(value = "/tripsList")
    public ModelAndView getTripsListView() {
        List<Trip> trips = tripService.getAllTrips();
        LOGGER.debug("trips.size = " + trips.size());
        return new ModelAndView("tripsList", "trips", trips);
    }

    @RequestMapping("/tripInputForm")
    public ModelAndView launchTripInputFormView() {
        return new ModelAndView("tripInputForm", "trip", new Trip());
    }

    @RequestMapping("/submitTripData")
    public ModelAndView getInputFormView(@RequestParam("DriverName") String driverName,
                                         @RequestParam("Car") String car,
                                         @RequestParam("FuelRate100") Double fuelRate100,
                                         @RequestParam("StartPoint") String startPoint,
                                         @RequestParam("EndPoint") String endPoint,
                                         @RequestParam("Distance") String distance,
                                         @RequestParam("StartDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam("EndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws ParseException {
        LOGGER.debug("add new trip");
        Trip trip = checkTripFields(new Trip(), driverName, car, fuelRate100, startPoint, endPoint, distance, startDate, endDate);
        if (trip != null) {
            tripService.addTrip(trip);
            return getTripsListView();
        } else {
            return getErrorPage("Введены некорректные данные. Проверьте правильность ввода");
        }
    }

    @RequestMapping(value = "/tripRemoveForm")
    public ModelAndView removeDriverFormView() {
        LOGGER.debug("get all available trips");
        List<Trip> trips = tripService.getAllTrips();
        return new ModelAndView("removeTrip", "trips", trips);
    }

    @RequestMapping(value = "/tripRemove")
    public ModelAndView removeDriverView(@RequestParam("Id") Long tripId) {
        LOGGER.debug("Delete trip with id = " + tripId);
        Trip trip = tripService.getTripById(tripId);
        if (trip != null) {
            tripService.removeTrip(tripId);
            return getTripsListView();
        } else {
            return getErrorPage("Рейс с ID = " + tripId + " не существует");
        }
    }

    @RequestMapping(value = "/tripUpdateId")
    public ModelAndView updateDriverFormView() {
        LOGGER.debug("get all available trips");
        List<Trip> trips = tripService.getAllTrips();
        return new ModelAndView("updateTrip", "trips", trips);
    }

    @RequestMapping(value = "/tripUpdateForm")
    public ModelAndView getTripByIdView(@RequestParam("Id") Long tripId) {
        LOGGER.debug("trip.id = " + tripId);
        Trip trip = tripService.getTripById(tripId);
        if (trip != null) {
            return new ModelAndView("updateTripForm", "trip", trip);
        } else {
            return getErrorPage("Рейс с ID = " + tripId + " не существует");
        }
    }

    @RequestMapping(value = "/tripUpdate")
    public ModelAndView updateDriver(@RequestParam("Id") Long tripId,
                                     @RequestParam("DriverName") String driverName,
                                     @RequestParam("Car") String car,
                                     @RequestParam("FuelRate100") Double fuelRate100,
                                     @RequestParam("StartPoint") String startPoint,
                                     @RequestParam("EndPoint") String endPoint,
                                     @RequestParam("Distance") String distance,
                                     @RequestParam("StartDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                     @RequestParam("EndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws ParseException {
        LOGGER.debug("update trip with id = " + tripId);
        Trip trip = tripService.getTripById(tripId);
        trip = checkTripFields(trip, driverName, car, fuelRate100, startPoint, endPoint, distance, startDate, endDate);
        if (trip != null) {
            tripService.updateTrip(trip);
            return getTripsListView();
        } else {
            return getErrorPage("Введены некорректные данные. Проверьте правильность ввода");
        }
    }

    @RequestMapping(value = "/tripCountFuelDateForm")
    public String countFuelByDateFormView() {
        return "tripCountFuelDate";
    }

    @RequestMapping(value = "/tripCountFuelDate")
    public ModelAndView countFuelByDateView(@RequestParam("StartDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                            @RequestParam("EndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate)
            throws ParseException {
        LOGGER.debug(String.format("get sumFuel between dates %s - %s", format.format(startDate), format.format(endDate)));
        try {
            List<Trip> trips = tripService.getTripsByDate(startDate, endDate);
            Double sum = 0d;
            for (Trip current : trips) {
                sum += Double.parseDouble(current.getSumFuel().replace(',', '.'));
            }
            Map<String, Object> result = new HashMap<>();
            result.put("startDate", format.format(startDate));
            result.put("endDate", format.format(endDate));
            result.put("sum", String.format("%.2f", sum));
            result.put("trips", trips);
            return new ModelAndView("summaryByDates", "result", result);
        } catch (Exception e) {
            return getErrorPage("Введены некорректные данные. Проверьте правильность ввода");
        }
    }

    @RequestMapping(value = "/tripCountFuelDriverForm")
    public ModelAndView countFuelByDriverFormView() {
        LOGGER.debug("get list of available drivers");
        List<Driver> drivers = driverService.getAllDrivers();
        return new ModelAndView("tripCountFuelDriver", "drivers", drivers);
    }

    @RequestMapping(value = "/tripCountFuelDriver")
    public ModelAndView countFuelByDateView(@RequestParam("DriverId") Long driverId) {
        LOGGER.debug("get sumFuel for driver with ID = " + driverId);
        Driver driver = driverService.getDriverById(driverId);
        if (driver != null) {
            List<Trip> trips = tripService.getTripsByDriver(driver.getName());
            Double sum = 0d;
            for (Trip current : trips) {
                sum += Double.parseDouble(current.getSumFuel().replace(',', '.'));
            }
            Map<String, Object> result = new HashMap<>();
            result.put("driver", driver.getName());
            result.put("sum", String.format("%.2f", sum));
            result.put("trips", trips);
            return new ModelAndView("summaryByDriver", "result", result);
        } else return getErrorPage("Водитель с ID = " + driverId + " не существует");
    }

    private Trip checkTripFields(Trip trip, String driverName, String car, Double fuelRate100, String startPoint,
                                 String endPoint, String distance, Date startDate, Date endDate) {
        boolean errorStatus = false;
        Pattern isNumber = Pattern.compile("[0-9]+");

        Driver driver = driverService.getDriverByName(driverName);
        if (!errorStatus && driver != null)
            trip.setDriverName(driverName);
        else errorStatus = true;

        if (!errorStatus && !isNumber.matcher(car).matches())
            trip.setCar(car);
        else errorStatus = true;

        if (fuelRate100 > 0) trip.setFuelRate100(fuelRate100);
        else errorStatus = true;

        if (!errorStatus && !isNumber.matcher(startPoint).matches())
            trip.setStartPoint(startPoint);
        else errorStatus = true;

        if (!errorStatus && !isNumber.matcher(endPoint).matches())
            trip.setEndPoint(endPoint);
        else errorStatus = true;

        try {
            if (!errorStatus) {
                Double checker = Double.parseDouble(distance);
                trip.setDistance(distance);
                trip.setStartDate(startDate);
                trip.setEndDate(endDate);
                trip.setSumFuel();
                return trip;
            } else return null;
        } catch (NumberFormatException e) {
            errorStatus = true;
            return null;
        }
    }

    private ModelAndView getErrorPage(String exceptionMessage) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("datetime", format.format(new Date().getTime()));
        errorMap.put("exception", exceptionMessage);
        errorMap.put("url", "/drivers/driverInputForm");
        return new ModelAndView("error/404", "errorMap", errorMap);
    }
}
