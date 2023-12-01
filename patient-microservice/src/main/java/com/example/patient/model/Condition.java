package com.example.patient.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conditionType;
    private String description;
    private LocalDateTime startDate;
    @ManyToOne
    private Patient patient;

    public Condition() {
    }

    public Condition(String conditionType, String description, Patient patient) {
        this.conditionType = conditionType;
        this.description = description;
        this.patient = patient;
        this.startDate = LocalDateTime.now();
    }

    public Condition(String conditionType, String description, LocalDateTime startDate, Patient patient) {
        this.conditionType = conditionType;
        this.description = description;
        this.startDate = startDate;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}