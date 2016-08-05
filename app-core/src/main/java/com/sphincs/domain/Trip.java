package com.sphincs.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRIPS")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tripid")
    private Long id;

    private Driver driver;

    @Column(name = "tripdriver")
    private Long driverId;

    private Long getDriverForDB() {
        if (this.driver != null) return this.driver.getId();
        else throw new IllegalArgumentException();
    }

    @Column(name = "startpoint")
    private String startPoint;

    @Column(name = "endpoint")
    private String endPoint;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;

    @Column(name = "sumfuel")
    private Double sumFuel;

    private String sumFuelString = getSumFuelString(sumFuel);

    public String getSumFuelString(Double sumFuel) {
        String tempString = String.valueOf(sumFuel);
        if (tempString.indexOf('.') + 2 < tempString.length())
            this.sumFuelString = tempString.substring(0, tempString.indexOf('.') + 3);
        else this.sumFuelString = tempString;
        return this.sumFuelString;
    }

    public Trip() {
    }

    public Trip(Long id, Driver driver, String startPoint, String endPoint, Double distance, Date startDate, Date endDate) {
        this.id = id;
        this.driver = driver;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;
        if (this.distance == null || this.distance <= 0 || this.driver == null) this.sumFuel = 0D;
        else this.sumFuel = this.distance / 100 * this.driver.getFuelRate100();
        this.driverId = getDriverForDB();
    }

    public Long getId() {
        return id;
    }

    public Double getDistance() {
        return distance;
    }

    public Driver getDriver() {
        return driver;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public Double getSumFuel() {
        return sumFuel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setSumFuel() {
        if (this.distance > 0 && driver != null) this.sumFuel = (double) this.distance / 100 * this.driver.getFuelRate100();
        else this.sumFuel = 0D;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Trip trip = (Trip) obj;

        if (id != null ? !id.equals(trip.id) : trip.id != null) return false;
        if (driver != null ? !driver.equals(trip.driver) : trip.driver != null) return false;
        if (startPoint != null ? !startPoint.equals(trip.startPoint) : trip.startPoint != null) return false;
        if (endPoint != null ? !endPoint.equals(trip.endPoint) : trip.endPoint != null) return false;
        if (distance != null ? !distance.equals(trip.distance) : trip.distance != null) return false;
        if (startDate != null ? !startDate.equals(trip.startDate) : trip.startDate != null) return false;
        if (endDate != null ? !endDate.equals(trip.endDate) : trip.endDate != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Trip: {" +
                "id=" + id +
                "driver=" + driver +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", distance=" + distance +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}