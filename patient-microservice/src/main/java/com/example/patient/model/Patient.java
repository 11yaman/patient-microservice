package com.example.patient.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient extends User{

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;

    @OneToMany(mappedBy = "patient")
    private List<Note> notes;

    @OneToMany(mappedBy = "patient")
    private List<Encounter> encounters;

    @OneToMany(mappedBy = "patient")
    private List<Condition> conditions;

    public Patient() {
    }

    public Patient(Long id, String username, String firstName, String lastName, LocalDate birthDate) {
        super(id, username, firstName, lastName, birthDate, Role.PATIENT);
    }

    public Patient(String username, String password, String firstName, String lastName, LocalDate birthDate) {
        super(username, password, firstName, lastName, birthDate, Role.PATIENT);
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Encounter> getEncounters() {
        return new ArrayList<>(encounters);
    }

    public void setEncounters(List<Encounter> encounters) {
        this.encounters = encounters;
    }

    public List<Condition> getConditions() {
        return new ArrayList<>(conditions);
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
