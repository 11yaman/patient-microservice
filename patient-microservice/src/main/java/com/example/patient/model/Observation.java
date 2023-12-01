package com.example.patient.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Patient patient;
    private String type;
    private String value;
    private LocalDateTime onSetDateTime;
    @ManyToOne
    private Encounter encounter;

    public Observation() {
    }

    public Observation(Patient patient, String type, String value) {
        this.patient = patient;
        this.type = type;
        this.value = value;
        this.onSetDateTime = LocalDateTime.now();
    }

    public Observation(Patient patient,String type, String value, LocalDateTime dateTime) {
        this.patient = patient;
        this.type = type;
        this.value = value;
        this.onSetDateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return onSetDateTime;
    }

    public void setDate(LocalDateTime dateTime) {
        this.onSetDateTime = dateTime;
    }
}

