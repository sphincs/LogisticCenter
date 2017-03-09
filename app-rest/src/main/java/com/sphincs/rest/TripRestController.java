package com.sphincs.rest;

import com.sphincs.domain.Driver;
import com.sphincs.domain.RepresentationTrip;
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
//    private static Driver driver1;
//    private static Driver driver2;
//    private static Driver driver3;
//    private static Driver driver4;
//    private static Driver driver5;
//    private static Driver driver6;
//    private static Driver driver7;
//    private static Trip trip1;
//    private static Trip trip2;
//    private static Trip trip3;
//    private static Trip trip4;

    @Autowired
    public TripRestController(DriverService driverService, TripService tripService) {
        this.driverService = driverService;
        this.tripService = tripService;
    }

    @ResponseBody
    @RequestMapping(value = "/autofill", method = RequestMethod.GET)
    public void getFillTables() throws ParseException {
        new AutoFill(driverService, tripService).getFillTables();
    }

//    private void fillDrivers() {
//        driver1 = driverService.save(new Driver("Mike", 35));
//        driver2 = driverService.save(new Driver("Bobby", 42));
//        driver3 = driverService.save(new Driver("Misha", 36));
//        driver4 = driverService.save(new Driver("Boris", 37));
//        driver5 = driverService.save(new Driver("Spencer", 50));
//        driver6 = driverService.save(new Driver("Rob", 36));
//        driver7 = driverService.save(new Driver("Mitchel", 28));
//    }
//
//    private void fillTrips() throws ParseException {
//        trip1 = tripService.save(new Trip(driver1, "DAF", 13.8, "Brest", "Minsk", "350", Date.valueOf("2016-06-30"), Date.valueOf("2016-06-30")));
//        trip2 = tripService.save(new Trip(driver2, "FORD", 7.5, "Moscow", "Astana", "2800", Date.valueOf("2016-06-30"), Date.valueOf("2016-07-02")));
//        trip3 = tripService.save(new Trip(driver3, "BMW", 7.2, "Gomel", "Rome", "2530", Date.valueOf("2016-07-01"), Date.valueOf("2016-07-03")));
//        trip4 = tripService.save(new Trip(driver4, "LADA", 5.5, "Chicago", "Dallas", "3000", Date.valueOf("2016-07-04"), Date.valueOf("2016-07-06")));
//    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addTrip(@Valid @RequestBody final Trip trip) {
        return new ResponseEntity<>(tripService.save(trip).getId(), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ok(tripService.findAllTrips());
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return ok(tripService.findTripById(id));
    }

//    @ResponseBody
//    @RequestMapping(value = "/driver/{name}", method = RequestMethod.GET)
//    public ResponseEntity<List<Trip>> getTripsByDriver(@PathVariable String name) {
//        return ok(tripService.findByDriverName(name));
//    }

    @ResponseBody
    @RequestMapping(value = "/driver/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getTripsByDriver(@PathVariable Long driverId) {
        return ok(tripService.findByDriver(driverService.findDriverById(driverId)));
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
