package com.sphincs.domain;

import java.util.Set;

public class Driver {

    private Long id;
    private String name;
    private Integer age;
    private Set<Category> categories;
    private Car car;
    private String carNumber;
    private Double fuelRate100;

    public Driver() {
    }

    public Driver(Long id, String name, Integer age, Set<Category> categories, Car car, String carNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.categories = categories;
        this.car = car;
        this.carNumber = carNumber;
        this.fuelRate100 = car.getFuelRate();
    }

    public Integer getAge() {
        return age;
    }

    public Car getCar() {
        return car;
    }

    public Set<Category> getCategories() {
        return categories;
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

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

    public void setFuelRate100() {
        this.fuelRate100 = car.getFuelRate();
    }

    @Override
    public String toString() {
        String tempCategories = "";
        for (Category current : categories) {
            tempCategories += current.name() + ", ";
        }
        if (!tempCategories.equals("")) tempCategories = tempCategories.substring(0, tempCategories.length() - 2);

        return "Driver: {" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", categories=" + tempCategories +
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

        if (categories != null) {
            if (categories.size() != driver.categories.size()) return false;
            else {
                for (Category current : categories) {
                    if (!driver.categories.contains(current)) return false;
                }
            }
        } else if (driver.categories != null) return false;

        if (car != null ? !car.equals(driver.car) : driver.car != null) return false;
        if (carNumber != null ? !carNumber.equals(driver.carNumber) : driver.carNumber != null) return false;
        if (fuelRate100 != null ? !fuelRate100.equals(driver.fuelRate100) : driver.fuelRate100 != null) return false;

        return true;
    }

}
