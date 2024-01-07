package com.example.patient.mapping;

import com.example.patient.dto.response.EncounterDto;
import com.example.patient.dto.response.NoteDto;
import com.example.patient.dto.response.UserDto;
import com.example.patient.model.Encounter;
import com.example.patient.model.Note;
import com.example.patient.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoteMapper implements StrategyMapper<Note, NoteDto> {
    private final StrategyMapper<Encounter, EncounterDto> encounterMapper;
    private final StrategyMapper<User, UserDto> userMapper;

    @Autowired
    public NoteMapper(EncounterMapper encounterMapper, StrategyMapper<User, UserDto> userMapper) {
        this.encounterMapper = encounterMapper;
        this.userMapper = userMapper;
    }

    @Override
    public NoteDto map(Note source) {
        return new NoteDto(source.getId(), source.getText(), source.getDateTimeCreated(),
                userMapper.map(source.getEmployee()), userMapper.map(source.getPatient()),
                encounterMapper.map(source.getEncounter()));
    }

    @Override
    public List<NoteDto> mapAll(List<Note> source) {
        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note n:
                source) {
            noteDtos.add(map(n));
        }
        return noteDtos;
    }
}
