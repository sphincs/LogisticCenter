package com.sphincs.domain;

import javax.persistence.*;

@Entity
@Table(name = "DRIVERS", uniqueConstraints = @UniqueConstraint(columnNames = {"driverid", "drivername", "carnumber"}))
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "driverid")
    private Long id;

    @Column(name = "drivername")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "car")
    private String car;

    @Column(name = "carnumber")
    private String carNumber;

    @Column(name = "fuelrate")
    private Double fuelRate100;

    public Driver() {
    }

    public Driver(Long id, String name, Integer age, String car, String carNumber, Double fuelRate100) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.car = car;
        this.carNumber = carNumber;
        this.fuelRate100 = fuelRate100;
    }

    public Integer getAge() {
        return age;
    }

    public String getCar() {
        return car;
    }

    public Double getFuelRate100() {
        return fuelRate100;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setFuelRate100(Double fuelRate100) {
        this.fuelRate100 = fuelRate100;
    }

    @Override
    public String toString() {
        return "Driver: {" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", car=" + car +
                ", carNumber=" + carNumber +
                ", fuelRate100=" + fuelRate100 +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Driver driver = (Driver) obj;

        if (id != null ? !id.equals(driver.id) : driver.id != null) return false;
        if (name != null ? !name.equals(driver.name) : driver.name != null) return false;
        if (age != null ? !age.equals(driver.age) : driver.age != null) return false;
        if (car != null ? !car.equals(driver.car) : driver.car != null) return false;
        if (carNumber != null ? !carNumber.equals(driver.carNumber) : driver.carNumber != null) return false;
        if (fuelRate100 != null ? !fuelRate100.equals(driver.fuelRate100) : driver.fuelRate100 != null) return false;

        return true;
    }
}
