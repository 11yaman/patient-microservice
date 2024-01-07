package com.example.patient.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDateTime dateTimeCreated;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    @Nullable
    private Encounter encounter;

    public Note() {
    }

    public Note(String text, Employee employee, Patient patient, @Nullable Encounter encounter) {
        this.text = text;
        this.employee = employee;
        this.patient = patient;
        this.encounter = encounter;
        this.dateTimeCreated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Nullable
    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(@Nullable Encounter encounter) {
        this.encounter = encounter;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
}
