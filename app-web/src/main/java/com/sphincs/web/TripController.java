package com.sphincs.web;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.DriverService;
import com.sphincs.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/trips")
public class TripController {

    private static final Logger LOGGER = LogManager.getLogger();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private DriverService driverService;

    @Autowired
    private TripService tripService;

    @RequestMapping("/")
    public String init() {
        return "redirect:/trips/tripsList";
    }

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

    @RequestMapping("/submitData")
    public ModelAndView getInputFormView(@RequestParam("DriverName") String driverName,
                                   @RequestParam("StartPoint") String startPoint,
                                   @RequestParam("EndPoint") String endPoint,
                                   @RequestParam("Distance") String distance,
                                   @RequestParam("StartDate") String startDate,
                                   @RequestParam("EndDate") String endDate) throws ParseException {
        LOGGER.debug("add new trip");
        Trip trip = checkTripFields(new Trip(), driverName, startPoint, endPoint, distance, startDate, endDate);
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
                               @RequestParam("StartPoint") String startPoint,
                               @RequestParam("EndPoint") String endPoint,
                               @RequestParam("Distance") String distance,
                               @RequestParam("StartDate") String startDate,
                               @RequestParam("EndDate") String endDate) throws ParseException {
        LOGGER.debug("update trip with id = " + tripId);
        Trip trip = tripService.getTripById(tripId);
        trip = checkTripFields(trip, driverName, startPoint, endPoint, distance, startDate, endDate);
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
    public ModelAndView countFuelByDateView(@RequestParam("StartDate") String startDate,
                                            @RequestParam("EndDate") String endDate) throws ParseException {
        LOGGER.debug(String.format("get sumFuel between dates %s - %s", startDate, endDate));
        List<Trip> trips = tripService.getTripsByDate(format.parse(startDate), format.parse(endDate));
        Double sum = 0d;
        for (Trip current : trips) {
            sum += current.getSumFuel();
        }
        Map<String, String> result = new HashMap<>();
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("sum", new Trip().getSumFuelString(sum));
        return new ModelAndView("summaryByDates", "result", result);
    }

    @RequestMapping(value = "/tripCountFuelDriverForm")
    public ModelAndView countFuelByDriverFormView() {
        LOGGER.debug("get list of available drivers");
        List<Driver> drivers = driverService.getAllDrivers();
        return new ModelAndView("tripCountFuelDriver", "drivers", drivers);
    }

    @RequestMapping(value = "/tripCountFuelDriver")
    public ModelAndView countFuelByDateView(@RequestParam("DriverId") String driverId) {
        LOGGER.debug("get sumFuel for driver with ID = " + driverId);
        Driver driver = driverService.getDriverById(Long.parseLong(driverId));
        if (driver != null) {
            List<Trip> trips = tripService.getTripsByDriver(driver.getName());
            Double sum = 0d;
            for (Trip current : trips) {
                sum += current.getSumFuel();
            }
            Map<String, String> result = new HashMap<>();
            result.put("driver", driver.getName());
            Trip trip = new Trip();
            result.put("sum", trip.getSumFuelString(sum));
            return new ModelAndView("summaryByDriver", "result", result);
        } else return getErrorPage("Водитель с ID = " + driverId + " не существует");
    }

    private Trip checkTripFields(Trip trip, String driverName, String startPoint, String endPoint, String distance,
                                     String startDate, String endDate) {
        boolean errorStatus = false;
        Pattern isNumber = Pattern.compile("[0-9]+");

        Driver driver = driverService.getDriverByName(driverName);
        if (!errorStatus && driver != null)
            trip.setDriver(driver);
        else errorStatus = true;

        if (!errorStatus && !isNumber.matcher(startPoint).matches())
            trip.setStartPoint(startPoint);
        else errorStatus = true;

        if (!errorStatus && !isNumber.matcher(endPoint).matches())
            trip.setEndPoint(endPoint);
        else errorStatus = true;

        try {
            if (!errorStatus)
                trip.setDistance(Double.parseDouble(distance));
        } catch (NumberFormatException e) {
            errorStatus = true;
        }

        Date start = null;
        Date end = null;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
            if (start.after(end)) errorStatus = true;
        } catch (ParseException e) {
            errorStatus = true;
        }
        if (!errorStatus) {
            trip.setStartDate(start);
            trip.setEndDate(end);
            trip.setSumFuel();
            return trip;
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
