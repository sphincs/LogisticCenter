package com.sphincs.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DRIVERS", uniqueConstraints = @UniqueConstraint(columnNames = {"driverid", "drivername", "carnumber"}))
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "driverid")
    private Long id;

    @Size(min = 2, max = 100,
            message = "Driver's name must be between 2 and 100 characters of roman alphabet. Space is allow. ")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]+",
            message = "Driver's name must contain only roman letters. ")
    @Column(name = "drivername")
    private String name;

    @Min(value = 18,
            message = "Driver's age must be no less than 18 ages. ")
    @Max(value = 65,
            message = "Driver's age must be no more than 65 ages. ")
    @Column(name = "age")
    private Integer age;

    public Driver() {
    }

    public Driver(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Driver: {" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
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

        return true;
    }

}
