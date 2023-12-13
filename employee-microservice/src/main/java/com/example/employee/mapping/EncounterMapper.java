package com.example.employee.mapping;

import com.example.employee.dto.response.EncounterDto;
import com.example.employee.dto.response.UserDto;
import com.example.employee.model.Encounter;
import com.example.employee.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EncounterMapper implements StrategyMapper<Encounter, EncounterDto> {
    private final StrategyMapper<User, UserDto> userMapper;

    public EncounterMapper(StrategyMapper<User, UserDto> userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public EncounterDto map(Encounter source) {
        return new EncounterDto(
                source.getId(),
                userMapper.map(source.getPatient()),
                userMapper.map(source.getEmployee()),
                source.getDateTime()
        );
    }

    @Override
    public List<EncounterDto> mapAll(List<Encounter> source) {
        List<EncounterDto> encounterDtos = new ArrayList<>();
        for (Encounter encounter : source) {
            encounterDtos.add(map(encounter));
        }
        return encounterDtos;
    }
}
