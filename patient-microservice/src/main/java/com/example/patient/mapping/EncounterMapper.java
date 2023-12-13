package com.example.patient.mapping;

import com.example.patient.dto.response.EncounterDto;
import com.example.patient.model.Encounter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EncounterMapper implements StrategyMapper<Encounter, EncounterDto> {
    @Override
    public EncounterDto map(Encounter source) {
        return null;
    }

    @Override
    public List<EncounterDto> mapAll(List<Encounter> source) {
        return null;
    }
}
