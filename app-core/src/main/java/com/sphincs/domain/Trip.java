package com.sphincs.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "TRIPS", uniqueConstraints = @UniqueConstraint(columnNames = {"tripid"}))
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tripid")
    private Long id;

    @Size(min = 2, max = 100,
            message = "Driver's name must be between 2 and 100 characters of roman alphabet. Space is allow. ")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]+",
            message = "Driver's name must contain only roman letters. ")
    @Column(name = "tripdriver")
    private String driverName;

    @Size(min = 2, max = 20,
            message = "Car's name must be between 2 and 20 characters of roman alphabet. Space is allow. ")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]+",
            message = "Car's name must contain only roman letters. ")
    @Column(name = "car")
    private String car;

    @Min(value = 1,
            message = "Fuel rate must be at least 1 liter per 100 km. ")
    @Column(name = "fuelrate")
    private Double fuelRate100;

    @Size(min = 2, max = 20,
            message = "Start point must be between 2 and 20 characters of roman alphabet. Space is allow. ")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]+",
            message = "Start point must contain only roman letters. ")
    @Column(name = "startpoint")
    private String startPoint;

    @Size(min = 2, max = 20,
            message = "End point must be between 2 and 20 characters of roman alphabet. Space is allow. ")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]+",
            message = "End point must contain only roman letters. ")
    @Column(name = "endpoint")
    private String endPoint;

    @Pattern(regexp = "^[1-9][0-9]{0,4}",
            message = "Distance must be between 1 and 5 numerics. ")
    @Column(name = "distance")
    private String distance;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;

    @Column(name = "sumfuel")
    private String sumFuel;

    public Trip() {
    }

    public Trip(Long id, String driverName, String car, Double fuelRate100, String startPoint,
                String endPoint, String distance, Date startDate, Date endDate) {
        this.id = id;
        this.driverName = driverName;
        this.car = car;
        this.fuelRate100 = fuelRate100;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;
        setSumFuel();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public Double getFuelRate100() {
        return fuelRate100;
    }

    public void setFuelRate100(Double fuelRate100) {
        this.fuelRate100 = fuelRate100;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSumFuel() {
        return sumFuel;
    }

    public void setSumFuel() {
        try {
            if (this.distance == null || Double.parseDouble(this.distance) <= 0) this.sumFuel = "0";
            else this.sumFuel = String.format("%.2f", Double.parseDouble(this.distance) / 100 * this.fuelRate100);
        } catch (NumberFormatException e) {
            this.sumFuel = "0";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Trip trip = (Trip) obj;

        if (id != null ? !id.equals(trip.id) : trip.id != null) return false;
        if (driverName != null ? !driverName.equals(trip.driverName) : trip.driverName != null) return false;
        if (car != null ? !car.equals(trip.car) : trip.car != null) return false;
        if (fuelRate100 != null ? !fuelRate100.equals(trip.fuelRate100) : trip.fuelRate100 != null) return false;
        if (startPoint != null ? !startPoint.equals(trip.startPoint) : trip.startPoint != null) return false;
        if (endPoint != null ? !endPoint.equals(trip.endPoint) : trip.endPoint != null) return false;
        if (distance != null ? !distance.equals(trip.distance) : trip.distance != null) return false;
        if (startDate != null ? !startDate.equals(trip.startDate) : trip.startDate != null) return false;
        if (endDate != null ? !endDate.equals(trip.endDate) : trip.endDate != null) return false;
        if (sumFuel != null ? !sumFuel.equals(trip.sumFuel) : trip.sumFuel != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Trip: {" +
                "id=" + id +
                "driverName=" + driverName +
                ", car=" + car +
                ", fuelRate100=" + fuelRate100 +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", distance=" + distance +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sumFuel=" + sumFuel +
                '}';
    }
}