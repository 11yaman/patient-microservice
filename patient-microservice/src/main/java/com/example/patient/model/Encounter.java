package com.example.patient.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Employee employee;
    private LocalDateTime dateTime;

    //TODO: CASCADE TYPE
    @OneToMany(mappedBy = "encounter")
    private List<Note> notes;
    @OneToMany(mappedBy = "encounter")
    private List<Observation> observations;

    public Encounter() {
    }

    public Encounter(Patient patient, Employee employee, LocalDateTime dateTime) {
        this.patient = patient;
        this.employee = employee;
        this.dateTime = dateTime;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Observation> getObservations() {
        return new ArrayList<>(observations);
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }
}