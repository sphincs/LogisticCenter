package com.sphincs.rest;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.DriverService;
import com.sphincs.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/trips")
public class TripRestController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private TripService tripService;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addTrip(@Valid @RequestBody final Trip trip, final BindingResult result) {
        if (result.hasErrors()) return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        try {
            if (driverService.getDriverByName(trip.getDriverName()) == null) {
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "This driver not exist. ")), HttpStatus.BAD_REQUEST);
            }
            if (trip.getStartDate().after(trip.getEndDate()))
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "Check the dates, please. ")), HttpStatus.BAD_REQUEST);
            Long tripId = tripService.addTrip(trip);
            if (tripId == null) {
                return new ResponseEntity("Trip = null. ", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(tripId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return new ResponseEntity(trips, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        if (trip == null) {
            return new ResponseEntity("Trip with id = " + id + " not found. ", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trip, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/driver/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDriver(@PathVariable String name) {
        List<Trip> trips = tripService.getTripsByDriver(name);
        if (trips == null) {
            return new ResponseEntity("Trips with driverName = " + name + " not found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/route/{startPoint}/{endPoint}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByRoute(
            @PathVariable String startPoint,
            @PathVariable String endPoint) {
        List<Trip> trips = tripService.getTripsByRoute(startPoint, endPoint);
        if (trips == null) {
            return new ResponseEntity(String.format("Trips with route form %s to %s not found. ", startPoint, endPoint),
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/car/{car}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByCar(@PathVariable String car) {
        List<Trip> trips = tripService.getTripsByCar(car);
        if (trips == null) {
            return new ResponseEntity(String.format("Trips with car = %s not found. ", car), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeTrip(@PathVariable Long id) {
        tripService.removeTrip(id);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateTrip(@Valid @RequestBody final Trip trip, final BindingResult result) {
        if (result.hasErrors()) return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        try {
            if (driverService.getDriverByName(trip.getDriverName()) == null)
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "This driver not exist. ")), HttpStatus.BAD_REQUEST);
            if (trip.getStartDate().after(trip.getEndDate()))
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "Check the dates, please. ")), HttpStatus.BAD_REQUEST);
            tripService.updateTrip(trip);
            return new ResponseEntity("{}", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/date/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDate(
            @PathVariable String startDate,
            @PathVariable String endDate) throws ParseException {
        Date start = formatter.parse(startDate);
        Date end = formatter.parse(endDate);
        List<Trip> trips = tripService.getTripsByDate(start, end);
        if (trips.isEmpty()) {
            return new ResponseEntity(String.format("Trips with date from %s to %s not found. ",
                    formatter.format(startDate),
                    formatter.format(endDate)),
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/sumFuel/driver/{name}", method = RequestMethod.GET)
    public ResponseEntity countFuelByDriver(@PathVariable String name) {
        Driver driver = driverService.getDriverByName(name);
        if (driver != null) {
            List<Trip> trips = tripService.getTripsByDriver(name);
            Double sum = 0d;
            for (Trip current : trips) {
                sum += Double.parseDouble(current.getSumFuel().replace(',', '.'));
            }
            Map<String, Object> result = new HashMap<>();
            result.put("driver", driver.getName());
            result.put("sum", String.format("%.2f", sum));
            result.put("trips", trips);
            return new ResponseEntity(result, HttpStatus.OK);
        } else return new ResponseEntity("Driver " + name + " not exist", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @RequestMapping(value = "/sumFuel/date/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity countFuelByDate(@PathVariable String startDate,
                                          @PathVariable String endDate) throws ParseException {
        Date start = formatter.parse(startDate);
        Date end = formatter.parse(endDate);
        List<Trip> trips = tripService.getTripsByDate(start, end);
        Double sum = 0d;
        Map<String, Object> result = new HashMap<>();
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        if (!trips.isEmpty()) {
            for (Trip current : trips) {
                sum += Double.parseDouble(current.getSumFuel().replace(',', '.'));
            }
        }
        result.put("sum", String.format("%.2f", sum));
        result.put("trips", trips);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
