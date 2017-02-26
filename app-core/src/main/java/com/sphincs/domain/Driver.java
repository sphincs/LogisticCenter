package com.sphincs.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class Driver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 100,
            message = "Driver's name must be between 2 and 100 characters of roman alphabet. Space is allow. ")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]+",
            message = "Driver's name must contain only roman letters. ")
    @Column(unique = true)
    private String name;

    @Min(value = 18,
            message = "Driver's age must be no less than 18 ages. ")
    @Max(value = 65,
            message = "Driver's age must be no more than 65 ages. ")
    private int age;

    public Driver() {
    }

    public Driver(String name, int age) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Driver driver = (Driver) obj;

        if (id != null ? !id.equals(driver.id) : driver.id != null) return false;
        if (name != null ? !name.equals(driver.name) : driver.name != null) return false;
        if (age != driver.age) return false;


        return true;
    }

    @Override
    public String toString() {
        return "Driver: {" +
                "id=" + id +
                "name=" + name +
                ", age=" + age +
                '}';
    }

}

