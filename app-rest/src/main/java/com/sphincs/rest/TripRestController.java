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
import java.sql.Date;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trips")
public class TripRestController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private TripService tripService;

    @ResponseBody
    @RequestMapping(value = "/autofill", method = RequestMethod.GET)
    public void getFillTables() throws ParseException {
        fillDrivers();
        fillTrips();
    }

    private void fillDrivers() {
        driverService.save(new Driver("Mike", 35));
        driverService.save(new Driver("Bobby", 42));
        driverService.save(new Driver("Spencer", 50));
        driverService.save(new Driver("Misha", 36));
        driverService.save(new Driver("Rob", 36));
        driverService.save(new Driver("Boris", 37));
        driverService.save(new Driver("Mitchel", 28));
    }

    private void fillTrips() throws ParseException {
        tripService.save(new Trip("Mike", "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")));
        tripService.save(new Trip("Bobby", "FORD", 7.5, "Moscow", "Astana", "2800", Date.valueOf("2016-06-30"), Date.valueOf("2016-07-02")));
        tripService.save(new Trip("Misha", "BMW", 7.2, "Gomel", "Rome", "2530", Date.valueOf("2016-07-01"), Date.valueOf("2016-07-03")));
        tripService.save(new Trip("Boris", "LADA", 5.5, "Chicago", "Dallas", "3000", Date.valueOf("2016-07-04"), Date.valueOf("2016-07-06")));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addTrip(@Valid @RequestBody final Trip trip, final BindingResult result) {
        if (result.hasErrors()) return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        String driverName = trip.getDriverName();
        Date start = trip.getStartDate();
        Date end = trip.getEndDate();
        try {
            if (driverService.findByName(trip.getDriverName()) == null) {
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "This driver not exist. ")), HttpStatus.BAD_REQUEST);
            }
            if (trip.getStartDate().after(trip.getEndDate()))
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "Check the dates, please. ")), HttpStatus.BAD_REQUEST);
            if (isDriverFree(driverName, start, end)) {
                tripService.save(trip);
                return new ResponseEntity("{}", HttpStatus.CREATED);
            } else return new ResponseEntity(Collections.singletonMap("defaultMessage",
                    Collections.singletonMap("defaultMessage", "This driver not available in this dates. ")), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isDriverFree(String driverName, Date start, Date end) {
        List<Trip> trips = tripService.findByDriverName(driverName);
        if (trips.isEmpty()) return true;
        for (Trip current : trips) {
            if ((start.after(current.getStartDate()) && start.before(current.getEndDate())) ||
                    (end.after(current.getStartDate()) && end.before(current.getEndDate()))) {
                return false;
            }
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.findAll();
        return new ResponseEntity(trips, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.findById(id);
        if (trip == null) {
            return new ResponseEntity("Trip with id = " + id + " not found. ", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trip, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/driver/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDriver(@PathVariable String name) {
        List<Trip> trips = tripService.findByDriverName(name);
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
        List<Trip> trips = tripService.findByStartPointAndEndPoint(startPoint, endPoint);
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
        List<Trip> trips = tripService.findByCar(car);
        if (trips == null) {
            return new ResponseEntity(String.format("Trips with car = %s not found. ", car), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeTrip(@PathVariable Long id) {
        tripService.delete(id);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateTrip(@Valid @RequestBody final Trip trip, final BindingResult result) {
        if (result.hasErrors()) return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        String driverName = trip.getDriverName();
        Date start = trip.getStartDate();
        Date end = trip.getEndDate();
        try {
            if (driverService.findByName(trip.getDriverName()) == null)
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "This driver not exist. ")), HttpStatus.BAD_REQUEST);
            if (trip.getStartDate().after(trip.getEndDate()))
                return new ResponseEntity(Collections.singletonMap("defaultMessage",
                        Collections.singletonMap("defaultMessage", "Check the dates, please. ")), HttpStatus.BAD_REQUEST);
            if (isDriverFree(driverName, start, end)) {
                tripService.save(trip);
                return new ResponseEntity("{}", HttpStatus.OK);
            } else return new ResponseEntity(Collections.singletonMap("defaultMessage",
                    Collections.singletonMap("defaultMessage", "This driver not available in this dates. ")), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/date/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDate(
            @PathVariable String startDate,
            @PathVariable String endDate) throws ParseException {
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);
        List<Trip> trips = tripService.findByStartDateAndEndDate(start, end);
        if (trips.isEmpty()) {
            return new ResponseEntity(String.format("Trips with date from %s to %s not found. ",
                    startDate,
                    endDate),
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(trips, HttpStatus.OK);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/sumFuel/driver/{name}", method = RequestMethod.GET)
    public ResponseEntity countFuelByDriver(@PathVariable String name) {
        Driver driver = driverService.findByName(name);
        if (driver != null) {
            List<Trip> trips = tripService.findByDriverName(name);
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
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);
        List<Trip> trips = tripService.findByStartDateAndEndDate(start, end);
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
