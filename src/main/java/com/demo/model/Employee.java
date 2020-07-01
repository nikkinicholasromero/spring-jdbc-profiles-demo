package com.demo.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Employee {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private double salary;
    private LocalDate someDate;
    private LocalTime someTime;
    private LocalDateTime someDatetime;
    private boolean active;
}
