package com.example.patient.service.impl;

import com.example.patient.exception.NotFoundException;
import com.example.patient.model.Patient;
import com.example.patient.repository.PatientRepository;
import com.example.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public Patient getPatientByUsername(String username) {
        return patientRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
