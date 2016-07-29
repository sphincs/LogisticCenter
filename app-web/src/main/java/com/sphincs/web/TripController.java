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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ModelAndView launchDriverInputFormView() {
        return new ModelAndView("tripInputForm", "trip", new Trip());
    }

    @RequestMapping("/submitData")
    public String getInputFormView(@RequestParam("DriverName") String driverName,
                                   @RequestParam("StartPoint") String startPoint,
                                   @RequestParam("EndPoint") String endPoint,
                                   @RequestParam("Distance") Double distance,
                                   @RequestParam("StartDate") String startDate,
                                   @RequestParam("EndDate") String endDate) throws ParseException {
        Trip trip = new Trip();
        trip.setDriver(driverService.getDriverByName(driverName));
        trip.setStartPoint(startPoint);
        trip.setEndPoint(endPoint);
        trip.setDistance(distance);
        trip.setStartDate(format.parse(startDate));
        trip.setEndDate(format.parse(endDate));
        trip.setSumFuel();
        tripService.addTrip(trip);
        return "redirect:/trips/tripsList";
    }

    @RequestMapping(value = "/tripRemoveForm")
    public String removeDriverFormView() {
        return "removeTrip";
    }

    @RequestMapping(value = "/tripRemove")
    public String removeDriverView(@RequestParam("Id") Long tripId) {
        LOGGER.debug("Delete trip with id = " + tripId);
        tripService.removeTrip(tripId);
        return "redirect:/trips/tripsList";
    }

    @RequestMapping(value = "/tripUpdateId")
    public String updateDriverFormView() {
        return "updateTrip";
    }

    @RequestMapping(value = "/tripUpdateForm")
    public ModelAndView getTripByIdView(@RequestParam("Id") Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        LOGGER.debug("trip.id = " + tripId);
        return new ModelAndView("updateTripForm", "trip", trip);
    }

    @RequestMapping(value = "/tripUpdate")
    public String updateDriver(@RequestParam("Id") Long tripId,
                               @RequestParam("DriverName") String driverName,
                               @RequestParam("StartPoint") String startPoint,
                               @RequestParam("EndPoint") String endPoint,
                               @RequestParam("Distance") Double distance,
                               @RequestParam("StartDate") String startDate,
                               @RequestParam("EndDate") String endDate) throws ParseException {
        LOGGER.debug("update trip with id = " + tripId);
        Trip trip = tripService.getTripById(tripId);
        trip.setDriver(driverService.getDriverByName(driverName));
        trip.setStartPoint(startPoint);
        trip.setEndPoint(endPoint);
        trip.setDistance(distance);
        trip.setStartDate(format.parse(startDate));
        trip.setEndDate(format.parse(endDate));
        trip.setSumFuel();
        tripService.updateTrip(trip);
        return "redirect:/trips/tripsList";
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
    public ModelAndView countFuelByDateView(@RequestParam("DriverName") String driverName) {
        LOGGER.debug("get sumFuel for driver " + driverName);
        List<Trip> trips = tripService.getTripsByDriver(driverName);
        Double sum = 0d;
        for (Trip current : trips) {
            sum += current.getSumFuel();
        }
        Map<String, String> result = new HashMap<>();
        result.put("driver", driverName);
        Trip trip = new Trip();
        result.put("sum", trip.getSumFuelString(sum));
        return new ModelAndView("summaryByDriver", "result", result);
    }
}
