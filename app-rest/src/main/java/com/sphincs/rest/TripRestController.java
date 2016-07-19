package com.sphincs.rest;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/trips")
public class TripRestController {

    @Autowired
    private TripService tripService;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addTrip(@RequestBody Trip trip) {
        try {
            Long tripId = tripService.addTrip(trip);
            if (tripId == null) {
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(tripId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return new ResponseEntity(trips, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        if (trip == null) {
            return new ResponseEntity("Trip with id = " + id + " not found.", HttpStatus.NOT_FOUND);
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
            return new ResponseEntity(String.format("Trips with route = %s - %s not found", startPoint, endPoint), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeTrip(@PathVariable Long id) {
        tripService.removeTrip(id);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateTrip(@RequestBody Trip trip) {
        try {
            tripService.updateTrip(trip);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value ="/date/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDate(
            @PathVariable String startDate,
            @PathVariable String endDate) throws ParseException {
        Date start = formatter.parse(startDate);
        Date end = formatter.parse(endDate);
        List<Trip> trips = tripService.getTripsByDate(start, end);
        if (trips == null) {
            return new ResponseEntity(String.format("Trips with date fron %s to %s not found",
                    formatter.format(startDate),
                    formatter.format(endDate)),
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }




}
