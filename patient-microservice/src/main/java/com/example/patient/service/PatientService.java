package com.example.patient.service;

import com.example.patient.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    Patient getPatientById(Long patientId);

    Patient getPatientByUsername(String name);
}
