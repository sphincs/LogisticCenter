package com.sphincs.rest;

import com.sphincs.domain.Driver;
import com.sphincs.domain.Trip;
import com.sphincs.service.DriverService;
import com.sphincs.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/trips")
public class TripRestController {

    private final DriverService driverService;
    private final TripService tripService;

    @Autowired
    public TripRestController(DriverService driverService, TripService tripService) {
        this.driverService = driverService;
        this.tripService = tripService;
    }

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
    public ResponseEntity<Long> addTrip(@Valid @RequestBody final Trip trip) {
        return new ResponseEntity<>(tripService.save(trip).getId(), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ok(tripService.findAll());
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return ok(tripService.findById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/driver/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDriver(@PathVariable String name) {
        return ok(tripService.findByDriverName(name));
    }

    @ResponseBody
    @RequestMapping(value = "/route/{startPoint}/{endPoint}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByRoute(
            @PathVariable String startPoint,
            @PathVariable String endPoint) {
        return ok(tripService.findByStartPointAndEndPoint(startPoint, endPoint));
    }

    @ResponseBody
    @RequestMapping(value = "/car/{car}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByCar(@PathVariable String car) {
        return ok(tripService.findByCar(car));
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeTrip(@PathVariable Long id) {
        tripService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateTrip(@Valid @RequestBody final Trip trip) {
        tripService.save(trip);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(value = "/date/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDate(
            @PathVariable String startDate,
            @PathVariable String endDate) throws ParseException {
        return ok(tripService.findByStartDateAndEndDate(Date.valueOf(startDate), Date.valueOf(endDate)));
    }

    @ResponseBody
    @RequestMapping(value = "/sumFuel/driver/{name}", method = RequestMethod.GET)
    public ResponseEntity countFuelByDriver(@PathVariable String name) {
        return ok(tripService.countFuelByDriver(name));
    }

    @ResponseBody
    @RequestMapping(value = "/sumFuel/date/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity countFuelByDate(@PathVariable String startDate,
                                          @PathVariable String endDate) {
        return ok(tripService.countFuelByDate(startDate, endDate));
    }

}
