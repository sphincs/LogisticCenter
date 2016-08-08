package com.sphincs.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRIPS", uniqueConstraints = @UniqueConstraint(columnNames = {"tripid"}))
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tripid")
    private Long id;

    @Column(name = "tripdriver")
    private String driverName;

    @Column(name = "startpoint")
    private String startPoint;

    @Column(name = "endpoint")
    private String endPoint;

    @Column(name = "distance")
    private String distance;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;

    @Column(name = "sumfuel")
    private String sumFuel;

    private Driver driver;

    public Trip() {
    }

    public Trip(Long id, Driver driver, String startPoint, String endPoint, String distance, Date startDate, Date endDate) {
        this.id = id;
        this.driverName = driver.getName();
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;

        if (this.distance == null || Double.parseDouble(this.distance) <= 0 || driver == null) this.sumFuel = "0";
        else this.sumFuel = String.format("%.2f", Double.parseDouble(this.distance) / 100 * driver.getFuelRate100());
    }

    public Long getId() {
        return id;
    }

    public String getDistance() {
        return distance;
    }

    public String getDriverName() {
        return driverName;
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

    public String getSumFuel() {
        return sumFuel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
        if (this.distance == null || Double.parseDouble(this.distance) <= 0 || driver == null) this.sumFuel = "0";
        else
            this.sumFuel = String.format("%.2f", String.valueOf(Double.parseDouble(this.distance) / 100 * driver.getFuelRate100()));
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
        if (sumFuel != null ? !sumFuel.equals(trip.sumFuel) : trip.sumFuel != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Trip: {" +
                "id=" + id +
                "driverName=" + driverName +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", distance=" + distance +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sumFuel=" + sumFuel +
                '}';
    }
}