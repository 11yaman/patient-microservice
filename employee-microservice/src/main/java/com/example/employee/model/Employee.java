package com.example.employee.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee extends User{
    private Position position;

    @OneToMany(mappedBy = "employee")
    private List<Encounter> encounters;

    public enum Position {DOCTOR, OTHER}

    public Employee() {
    }

    public Employee(Long id, String userName, String password, String firstName, String lastName, LocalDate birthDate, Position position) {
        super(id, userName, password, firstName, lastName, birthDate, Role.EMPLOYEE);
        this.position = position;
    }

    public Employee(String userName, String password, String firstName, String lastName, LocalDate birthDate, Position position) {
        super(userName, password, firstName, lastName, birthDate, Role.EMPLOYEE);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public List<Encounter> getEncounters() {
        return encounters;
    }
}
