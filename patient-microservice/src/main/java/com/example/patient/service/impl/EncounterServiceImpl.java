package com.example.patient.service.impl;

import com.example.patient.model.Encounter;
import com.example.patient.repository.EncounterRepository;
import com.example.patient.service.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Not Implemented yet
 */
@Service
public class EncounterServiceImpl implements EncounterService {
    private final EncounterRepository encounterRepository;

    @Autowired
    public EncounterServiceImpl(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    @Override
    public Encounter getEncounterById(Long encounterId) {
        //return encounterRepository.findById(encounterId).orElse(null);
        return null;
    }
}
