package com.example.patient.service;

import com.example.patient.model.Patient;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    List<Patient> getAllPatients();

    Patient getPatientById(Long patientId);

    Patient getPatientByUsername(String name);
}
