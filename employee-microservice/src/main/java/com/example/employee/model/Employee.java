package com.example.employee.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Employee extends User{
    private Position position;
    public enum Position {DOCTOR, OTHER}

    public Employee() {
    }

    public Employee(String userName, String password, String firstName, String lastName, LocalDate birthDate, Position position) {
        super(userName, password, firstName, lastName, birthDate, Role.EMPLOYEE);
        this.position = position;
    }
}
