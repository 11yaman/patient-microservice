package com.example.patient.service;

import com.example.patient.model.Encounter;
import org.springframework.stereotype.Service;

@Service
public interface EncounterService {
    Encounter getEncounterById(Long encounterId);
}
