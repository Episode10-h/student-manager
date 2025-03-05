package org.example.studentmanager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "student_entity")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "student_name")
    @NotEmpty(message = "Name can not be empty")
    private String name;
    @Column
    @NotNull(message = "Age can not be empty")
    @Min(value = 18, message = "You have to be at least 18 years old.")
    private int age;
    @Column
    @Min(value = 0, message = "Zip code can not be smaller then 0")
    @Max(value = 9999, message = "Zip code can not be greater then 9999")
    @NotNull(message = "Zip code can not be empty")
    @Digits(integer = 6, fraction = 0, message = "Zip code is a 6 digits number.")
    private int zipCode;
    @Column
    @NotEmpty(message = "Country must be specified")
    private String country;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Student() {
    }

    public Student(String name, int age, int zipCode, String country, Status status) {
        this.name = name;
        this.age = age;
        this.zipCode = zipCode;
        this.country = country;
        //this.status = status;
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
