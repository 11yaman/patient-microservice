package com.example.patient.model;

import jakarta.persistence.*;

@Entity
public final class Employee extends User{
    private Position position;
    public enum Position {DOCTOR, OTHER}

    public Employee() {
    }
}
